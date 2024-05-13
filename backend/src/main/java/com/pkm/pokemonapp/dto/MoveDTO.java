package com.pkm.pokemonapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pkm.pokemonapp.enums.DamageClass;
import com.pkm.pokemonapp.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Move")
@AllArgsConstructor
@NoArgsConstructor
public class MoveDTO {

    @Id
    @JsonProperty("id")
    @Column(name = "id", nullable = false)
    private long id;

    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("power")
    @Column(name = "power", nullable = false)
    private int power;

    @JsonProperty("accuracy")
    @Column(name = "accuracy", nullable = false)
    private int accuracy;

    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @JsonProperty("damage_class")
    @Enumerated(EnumType.STRING)
    @Column(name = "damage_class", nullable = false)
    private DamageClass damageClass;

}
