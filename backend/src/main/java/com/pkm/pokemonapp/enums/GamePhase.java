package com.pkm.pokemonapp.enums;

public enum GamePhase {
    WAITING_FOR_ACTIONS,
    PLAYER_ACTIONS_VALIDATED,
    ROUND_START,
    BUILD_ACTION_QUEUE,
    EXECUTE_ACTION,
    DAMAGE_CALCULATION,
    FORCE_SWITCH_OUT,
    GAME_OVER
}
