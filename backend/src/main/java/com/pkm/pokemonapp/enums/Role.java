package com.pkm.pokemonapp.enums;

public enum Role {
    ADMIN, TRAINER;

    public String getName() {
        // Prefixing with "ROLE_" is a common convention in Spring Security.
        return "ROLE_" + this.name();
    }
}
