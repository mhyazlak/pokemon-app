package com.pkm.pokemonapp.enums;

public enum Type {
    NORMAL(0),
    FIRE(1),
    WATER(2),
    ELECTRIC(3),
    GRASS(4),
    ICE(5),
    FIGHTING(6),
    POISON(7),
    GROUND(8),
    FLYING(9),
    PSYCHIC(10),
    BUG(11),
    ROCK(12),
    GHOST(13),
    DARK(14),
    DRAGON(15),
    STEEL(16),
    FAIRY(17);

    private final int index;

    Type(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
