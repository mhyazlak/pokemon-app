package com.pkm.pokemonapp.model;

import com.pkm.pokemonapp.enums.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PlayerAction {

    @Getter
    ActionType actionType;

    @Getter
    long actionId;
}
