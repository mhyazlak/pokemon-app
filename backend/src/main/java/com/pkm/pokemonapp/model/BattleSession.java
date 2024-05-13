package com.pkm.pokemonapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class BattleSession {
    private String sessionId;
    SimpleGamestate gameState;
}