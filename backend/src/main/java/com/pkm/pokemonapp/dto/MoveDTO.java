package com.pkm.pokemonapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("power")
    private int power;

    @JsonProperty("accuracy")
    private int accuracy;

    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    private Type type;
}
