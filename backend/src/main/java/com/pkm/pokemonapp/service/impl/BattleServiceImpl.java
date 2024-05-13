package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dto.TeamDTO;
import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.enums.GamePhase;
import com.pkm.pokemonapp.model.SimpleGamestate;
import com.pkm.pokemonapp.model.Player;
import com.pkm.pokemonapp.model.PlayerAction;
import com.pkm.pokemonapp.model.PlayerSpecificGamestate;
import com.pkm.pokemonapp.service.IBattleService;
import com.pkm.pokemonapp.service.ITeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class BattleServiceImpl implements IBattleService {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ITeamService teamService;

    // Mapping sessionId to gamestate
    private final ConcurrentHashMap<String, SimpleGamestate> activeSessions = new ConcurrentHashMap<>();
    // Mapping users to sessionIds to find users faster if needed
    private final ConcurrentHashMap<UserDTO, String> userSessionMap = new ConcurrentHashMap<>();
    // redundant map to find users when the gamestate is progressing and we need to send
    // the new gamestate to the users
    private final ConcurrentHashMap<String,  Set<UserDTO>> sessionUserMap = new ConcurrentHashMap<>();

    @Override
    public void createBattleSession(String sessionId, Set<UserDTO> players) {
        // TODO Send individual Gamestate depending on player, hide stat info for enemy
        SimpleGamestate initGamestate = createInitialGamestate(players);

        activeSessions.put(sessionId, initGamestate);
        sessionUserMap.put(sessionId, players);
        for (UserDTO player : players) {
            PlayerSpecificGamestate playerGamestate = getPlayerGamestate(initGamestate, player.getId());
            messagingTemplate.convertAndSendToUser(player.getUsername(), "/battle/", playerGamestate);
        }
    }

    @Override
    public void removeBattleSession(String sessionId) {
        // Remove the session when the battle is over or aborted
        activeSessions.remove(sessionId);
    }

    @Override
    public boolean checkPlayerInSession(UserDTO userDTO) {
       return userSessionMap.get(userDTO) != null;
    }

    @Override
    public void handlePlayerAction(PlayerAction action, String sessionId, UserDTO user) {
        // Get the right session
        action.setPlayerId(user.getId());
        SimpleGamestate gamestate = activeSessions.get(sessionId);
        gamestate.validatePlayerAction(action);

        // incase after this action is validated and the round is proceeding inform both players
        if(gamestate.getGamePhase().equals(GamePhase.WAITING_FOR_ACTIONS)) {
            for(UserDTO userDTO : sessionUserMap.get(sessionId)) {
                messagingTemplate.convertAndSendToUser(userDTO.getUsername(), "/queue/", gamestate);
            }
        }
    }

    public SimpleGamestate createInitialGamestate(Set<UserDTO> users) {
        SimpleGamestate initGameState = new SimpleGamestate();
        for (UserDTO user : users) {
            TeamDTO playerTeam = teamService.getSelectedTeam(user.getId());
            Player player = new Player(user, playerTeam);
            initGameState.addPlayers(player);
        }
        initGameState.setGamePhase(GamePhase.WAITING_FOR_ACTIONS);
        return initGameState;
    }

    public PlayerSpecificGamestate getPlayerGamestate(SimpleGamestate currentGamestate, long playerId) {
        return new PlayerSpecificGamestate(currentGamestate, playerId);
    }

}
