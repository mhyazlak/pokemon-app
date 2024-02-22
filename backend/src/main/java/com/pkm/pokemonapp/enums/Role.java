package com.pkm.pokemonapp.enums;

import lombok.Getter;
import lombok.ToString;

@ToString(of = {"name"}, includeFieldNames = false)
public enum Role {
    ADMIN("admin", 1), USER("user", 2);

    @Getter
    private final String name;
    @Getter
    private final int id;

    private Role(final String name, final int id) {
        this.name = name;
        this.id = id;
    }

    public static Role fromString(final String text) throws IllegalArgumentException {
        if (text != null) {
            for (final Role type : Role.values()) {
                if (text.equalsIgnoreCase(type.name)) {
                    return type;
                }
            }
        }
        throw new IllegalArgumentException("unkown type [ " + text + " ]");
    }

    public static int getIdfromString(final String text) throws IllegalArgumentException {
        if (text != null) {
            for (final Role type : Role.values()) {
                if (text.equalsIgnoreCase(type.name)) {
                    return type.getId();
                }
            }
        }
        throw new IllegalArgumentException("unkown type [ " + text + " ]");
    }
}