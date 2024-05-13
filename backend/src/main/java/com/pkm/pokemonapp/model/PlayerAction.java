package com.pkm.pokemonapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pkm.pokemonapp.enums.ActionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PlayerAction implements Serializable {

    @JsonProperty("action_type")
    private ActionType actionType;

    @JsonIgnore
    private int speed; // needed for priority queue order
    @JsonProperty("move_index")
    private int moveIndex;
    @JsonIgnore
    private long playerId;
    @JsonProperty("target_index")
    private int targetIndex;
    @JsonIgnore
    private boolean concluded;

}
