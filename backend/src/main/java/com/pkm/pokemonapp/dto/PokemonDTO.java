package com.pkm.pokemonapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pkm.pokemonapp.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "POKEMON_VIEW")
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDTO {

    @Id
    @Column(name = "id", nullable = false)
    @JsonProperty("id")
    private long id;

    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_one", nullable = false)
    @JsonProperty("type_one")
    private Type typeOne;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_two", nullable = true) // This should be nullable as not every Pokemon has a second type
    @JsonProperty("type_two")
    private Type typeTwo;

    @Column(name = "base_hp", nullable = false)
    @JsonProperty("base_hp")
    private int baseHp;

    @Column(name = "base_atk", nullable = false)
    @JsonProperty("base_atk")
    private int baseAtk;

    @Column(name = "base_def", nullable = false)
    @JsonProperty("base_def")
    private int baseDef;

    @Column(name = "base_spa", nullable = false)
    @JsonProperty("base_spa")
    private int baseSpa;

    @Column(name = "base_spd", nullable = false)
    @JsonProperty("base_spd")
    private int baseSpd;

    @Column(name = "base_speed", nullable = false)
    @JsonProperty("base_speed")
    private int baseSpe;

    @Column(name = "base_total", nullable = false)
    @JsonProperty("base_total")
    private int baseTotal;

    @Column(name = "front_sprite", nullable = false)
    @JsonProperty("front_sprite")
    private String frontSprite;

    @Column(name = "back_sprite", nullable = false)
    @JsonProperty("back_sprite")
    private String backSprite;

    @Column(name = "icon_b64", nullable = false)
    @JsonProperty("icon_b64")
    private String icon_b64;

    @Column(name = "stage", nullable = false)
    @JsonProperty("stage")
    private int stage;
}
