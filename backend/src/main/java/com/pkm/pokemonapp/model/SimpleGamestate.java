package com.pkm.pokemonapp.model;


import com.pkm.pokemonapp.dto.MoveDTO;
import com.pkm.pokemonapp.dto.TeamMemberDTO;
import com.pkm.pokemonapp.enums.ActionType;
import com.pkm.pokemonapp.enums.DamageClass;
import com.pkm.pokemonapp.enums.GamePhase;
import com.pkm.pokemonapp.enums.Type;
import com.pkm.pokemonapp.service.exception.InvalidPlayerActionException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
@Slf4j
public class SimpleGamestate implements Serializable {

    private GamePhase gamePhase;
    Map<Long, Player> playerMap = new HashMap<>();
    Map<Long, PlayerAction> playerActionMap = new HashMap<>();
    Map<Long, TeamMemberDTO> selectedMemberMap = new HashMap<>();

    // Ids of players who are forced to switch
    Set<Long> forceSwitchOutSet = new HashSet<>();

    private Weather weather = Weather.CLEAR;
    private int round = 1;

    public void addPlayers(Player player) {
        playerMap.put(player.getPlayerId(), player);


    }

    public SimpleGamestate handlePlayerActions(PlayerAction playerAction) {
        // validate player action
        // if the action is valid, then put it in the playerActionMap
        validatePlayerAction(playerAction);
        // check if all necessary playeractions are present
        if (checkRequiredPlayerActionsPresent()) {
            this.gamePhase = GamePhase.PLAYER_ACTIONS_VALIDATED;
        } else {
            playerMap.get(playerAction.getPlayerId()).setWaitingForOpponent(true);
            this.gamePhase = GamePhase.WAITING_FOR_ACTIONS;
        }
        return this;
    }

    public SimpleGamestate startRound() {
        // if all playerActions are present clear player states
        // resetPlayerStates();




        // Set Player state to round start
        // for example forceSwitch
        resetPlayerStates();
        // put the correct teammembers for the calculation

        buildSelectedMemberMap();
        // proceed with queue building
        PriorityQueue<PlayerAction> actionQueue = buildActionQueue();

        log.debug("actionQueue is build");

        boolean forcePlayerAction = false;

        // Execute actions in order
        while (!actionQueue.isEmpty() && !forcePlayerAction) {
            PlayerAction action = actionQueue.poll();
            action.setConcluded(true);
            // Execute the action and update the game state accordingly
            executePlayerAction(action, selectedMemberMap);

            // If a pokemon is fainted, break the queue, check if all are fained else demand next member
            // check also if any non fainted pokemon is left, if yes force switch else set defeated
            forcePlayerAction = checkFainted(selectedMemberMap);
        }

        // Get player action before round end if necessary, example fainted pokemon
        if (forcePlayerAction) {
            this.gamePhase = GamePhase.FORCE_SWITCH_OUT;
            long playerId = 0;
        } else {
            // if there round is concluded, increase count and inform players
            // after execution and faint handling, increase round
            this.round++;
            this.gamePhase = GamePhase.WAITING_FOR_ACTIONS;
            this.playerActionMap.clear();
            // Return the updated game state
        }
        return this;
    }

    private void buildSelectedMemberMap() {
        for(Player player: this.playerMap.values()) {
            for(TeamMemberDTO teamMemberDTO : player.getTeam().getMembers()) {
                if(teamMemberDTO.isSelected()) {
                    this.selectedMemberMap.put(player.getPlayerId(), teamMemberDTO);
                }
            }
        }
    }

    private boolean checkRequiredPlayerActionsPresent() {
        return playerActionMap.size() == playerMap.size();
    }

    public void validatePlayerAction(PlayerAction playerAction) {
        Player player = playerMap.get(playerAction.getPlayerId());
        switch (this.gamePhase) {
            case WAITING_FOR_ACTIONS -> validateActionDuringWaiting(playerAction, player);
            default ->
                    // Consider logging an unexpected game phase value
                    throw new IllegalStateException("Unexpected game phase: " + this.gamePhase);
        }
    }

    private void validateActionDuringWaiting(PlayerAction playerAction, Player player) {
        if (this.playerActionMap.containsKey(playerAction.getPlayerId())) {
            throw new InvalidPlayerActionException("Player action is already provided.");
        }

        switch (playerAction.getActionType()) {
            case MOVE -> validateMoveAction(playerAction, player);
            default -> throw new InvalidPlayerActionException("Invalid action type.");
        }
    }

    private void validateMoveAction(PlayerAction playerAction, Player player) {
        TeamMemberDTO selectedMember = selectedMemberMap.get(playerAction.getPlayerId());
        if (!(playerAction.getMoveIndex() >= 0 && playerAction.getMoveIndex() < 4)) {
            throw new InvalidPlayerActionException("Move is not valid or selected member not found.");
        }
        this.playerActionMap.put(playerAction.getPlayerId(), playerAction);
        if(this.playerActionMap.size() == playerMap.size()) {
            startRound();

        }
    }

    private boolean hasMove(TeamMemberDTO selectedMember, Long moveId) {
        return selectedMember.getMoveset().stream().anyMatch(move -> move.getId() == moveId);
    }

    private void validateSwitchAction(PlayerAction playerAction, Player player) {
        if (isValidSwitch(playerAction, player)) {
            this.playerActionMap.put(playerAction.getPlayerId(), playerAction);
        } else {
            throw new InvalidPlayerActionException("Switch is not possible.");
        }
    }

    private boolean isValidSwitch(PlayerAction playerAction, Player player) {
        return playerAction.getTargetIndex() < player.getTeam().getMembers().size() &&
                player.getPokemonAlive() > 1 &&
                !player.getTeam().getMembers().get(playerAction.getTargetIndex()).isFainted();
    }

    private void validateForceSwitch(PlayerAction playerAction, Player player) {
        if (player.isForceSwitch() && playerAction.getActionType().equals(ActionType.SWITCH) && isValidSwitch(playerAction, player)) {
            this.playerActionMap.put(playerAction.getPlayerId(), playerAction);
        } else {
            throw new InvalidPlayerActionException("Forced switch is not possible or not required.");
        }
    }

    private void resetPlayerStates() {
        for (Map.Entry<Long, Player> entry : this.playerMap.entrySet()) {
            Player player = entry.getValue();
            player.setForceSwitch(false);
            player.setWaitingForOpponent(false);
        }
    }

    private boolean checkFainted(Map<Long, TeamMemberDTO> selectedMemberMap) {
        for (Map.Entry<Long, TeamMemberDTO> playerToMember : selectedMemberMap.entrySet()) {
            if (playerToMember.getValue().isFainted()) {
                // check if pokemon are left
                Player player = playerMap.get(playerToMember.getKey());
                if (player.getPokemonAlive() != 0) {
                    forceSwitchOutSet.add(player.getPlayerId());
                    player.setForceSwitch(true);
                    this.gamePhase = GamePhase.FORCE_SWITCH_OUT;
                } else {
                    player.setDefeated(true);
                    this.gamePhase = GamePhase.GAME_OVER;
                }
                return true;
            }
        }
        return false;
    }

    private PriorityQueue<PlayerAction> buildActionQueue() {
        Comparator<PlayerAction> comparator = Comparator.comparing(PlayerAction::getSpeed).reversed();
        PriorityQueue<PlayerAction> actionQueue = new PriorityQueue<>(comparator);

        selectedMemberMap.forEach((playerId, teamMemberDTO) -> {
            PlayerAction playerAction = this.playerActionMap.get(playerId);
            if (playerAction.getActionType().equals(ActionType.SWITCH)) {
                playerAction.setSpeed(Integer.MAX_VALUE);
            } else {
                playerAction.setSpeed(teamMemberDTO.getCurrentSpeed());
            }
            // TODO can you set the ID before this point?
            playerAction.setPlayerId(playerId);
            if (!this.playerActionMap.get(playerId).isConcluded()) {
                actionQueue.add(this.playerActionMap.get(playerId));
            }
        });
        return actionQueue;
    }

    private void executePlayerAction(PlayerAction action, Map<Long, TeamMemberDTO> selectedMemberMap) {

        TeamMemberDTO teamMember = selectedMemberMap.get(action.getPlayerId());
        MoveDTO move = teamMember.getMoveset().get(action.getMoveIndex());

        DamageClass damageClass = move.getDamageClass();
        TeamMemberDTO attackingPokemon = selectedMemberMap.get(action.getPlayerId());
        TeamMemberDTO defendingPokemon = selectedMemberMap.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(action.getPlayerId()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);

        int damage = 0;

        if (!damageClass.equals(DamageClass.STATUS)) {
            damage = calculateDamage_GEN_IV(move, attackingPokemon, defendingPokemon, damageClass);
            int remainingHp = defendingPokemon.getCurrentHp() - damage;
            if (remainingHp <= 0) {
                defendingPokemon.setCurrentHp(0);
                defendingPokemon.setFainted(true);
            } else {
                defendingPokemon.setCurrentHp(remainingHp);
            }
        } else {
            //TODO STATUS dmg class
        }
    }

    // TODO put this in GameRules
    private int calculateDamage_GEN_IV(MoveDTO move, TeamMemberDTO attackingPokemon,
                                       TeamMemberDTO defendingPokemon, DamageClass damageClass) {
        if (accuracyCheck()) {
            Random random = new Random();
            int movePower = move.getPower();
            int effectiveAttack = damageClass.equals(DamageClass.PHYSICAL) ? attackingPokemon.getCurrentAtk() : defendingPokemon.getCurrentSpa();
            int effectiveDefense = damageClass.equals(DamageClass.PHYSICAL) ? defendingPokemon.getCurrentDef() : defendingPokemon.getCurrentSpd();
            double burn = attackingPokemon.isBurned() ? 0.5 : 1;
            int critChance = 16; // TODO items and traits can alter this
            int critical = random.nextInt(critChance) == (critChance - 1) ? 2 : 1;
            double roll = 0.85 + (random.nextInt(16) / 100.0); // 0.85 to 1.00
            double stab = 1.0; // Default STAB value if no type matches
            Type moveType = move.getType();
            Type primaryType = attackingPokemon.getPokemon().getTypeOne();
            Type secondaryType = attackingPokemon.getPokemon().getTypeTwo();

            // Check primary type match
            if (moveType.equals(primaryType)) {
                stab = 1.5;
            } else if (secondaryType != null && moveType.equals(secondaryType)) {
                // Only check secondary type if it is not null
                stab = 1.5;
            }
            double type1 = GameRules.TYPE_EFFECTIVENESS[move.getType().getIndex()][defendingPokemon.getPokemon().getTypeOne().getIndex()];
            double type2 = defendingPokemon.getPokemon().getTypeTwo() != null ?
                    GameRules.TYPE_EFFECTIVENESS[move.getType().getIndex()][defendingPokemon.getPokemon().getTypeOne().getIndex()] : 1;



            return (int) 10;
        } else {
            return 0;
        }
    }

    // TODO put this in GameRules
    private int calculateWeatherModifier(Type attackType) {
        return 1;
    }

    // TODO put this in GameRules
    private boolean accuracyCheck() {
        return true;
    }
}