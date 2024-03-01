package com.pkm.pokemonapp.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.pkm.pokemonapp.service.impl.TeamServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;

@NoArgsConstructor
public class GameState implements Serializable {

    @JsonProperty("player_list")
    ArrayList<Player> playerList = new ArrayList<>();

    @JsonProperty("weather")
    private Weather weather = Weather.CLEAR;

    @JsonProperty("round")
    private int round = 1;

    public void addPlayer(Player player) {
        playerList.add(player);
    }

}
