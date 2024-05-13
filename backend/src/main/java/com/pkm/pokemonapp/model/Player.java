package com.pkm.pokemonapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pkm.pokemonapp.dto.TeamDTO;
import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.service.impl.TeamServiceImpl;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
public class Player {

    @JsonIgnore
    private long playerId;

    @JsonProperty("team")
    private TeamDTO team;

    @JsonProperty("force_switch")
    private boolean forceSwitch;

    @JsonProperty("defeated")
    private boolean defeated;

    @JsonProperty("pokemon_alive")
    private int pokemonAlive;

    //@JsonProperty("waiting_for_opponent")
    //private boolean waitingForOpponent;

    public Player(UserDTO user, TeamDTO team) {
        this.playerId = user.getId();
        this.team = team;
        this.pokemonAlive = team.getMembers().size();
    }

}
