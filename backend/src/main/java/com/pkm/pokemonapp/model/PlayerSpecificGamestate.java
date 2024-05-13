package com.pkm.pokemonapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pkm.pokemonapp.dto.TeamDTO;
import com.pkm.pokemonapp.dto.TeamMemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerSpecificGamestate {

    @JsonProperty("player")
    private Player player;

    @JsonProperty("opponent")
    private Player opponent;

    @JsonProperty("round")
    private int round;

    public PlayerSpecificGamestate(SimpleGamestate gamestate, Long playerId) {
        Player detailedOpponent = null;
        for (Player p : gamestate.getPlayerMap().values()) {
            if (p.getPlayerId() == playerId) {
                this.player = p;
            } else {
                detailedOpponent = p;
            }
        }
        // we need a deep copy for the opponent, to hide stats and intel, if not shown yet, also hide moveset
        Player specificOpponent = new Player();
        // populate the team for the opponent only with shown pokemon, else put null in the slots
        TeamDTO specificOpponentTeam = new TeamDTO();
        List<TeamMemberDTO> opponentMemberList = new ArrayList<>();

        // only add members who are already shown, and do not add their movesets, only the pokemon
        detailedOpponent.getTeam().getMembers().forEach(member -> {
            TeamMemberDTO shownMember = new TeamMemberDTO();
            shownMember.setPokemon(member.getPokemon());
            shownMember.setMaxHp(member.getMaxHp());
            shownMember.setCurrentHp(member.getCurrentHp());
            opponentMemberList.add(shownMember);
        });
        specificOpponentTeam.setMembers(opponentMemberList);
        specificOpponent.setTeam(specificOpponentTeam);
        this.opponent = specificOpponent;
    }
}
