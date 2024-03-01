package com.pkm.pokemonapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pkm.pokemonapp.dto.TeamDTO;
import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.service.impl.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


public class Player {

    @JsonProperty("user")
    private UserDTO user;

    @JsonProperty("team")
    private TeamDTO team;


    public Player(UserDTO user, TeamDTO team) {
        this.user = user;
        this.team = team;
    }

}
