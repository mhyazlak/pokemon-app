package com.pkm.pokemonapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Permission")
public class PermissionDTO {

    @Id
    @Getter
    @JsonProperty("id")
    @Column(name = "[id]", nullable = false)
    private long id;

    @JsonProperty("user_id")
    @Column(name = "[user_id]", nullable = false)
    private long userId;

    @JsonProperty("admin")
    @Column(name = "[admin]", nullable = false)
    private boolean admin;

    @JsonProperty("trainer")
    @Column(name = "[trainer]", nullable = false)
    private boolean trainer;
}
