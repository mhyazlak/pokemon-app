package com.pkm.pokemonapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pkm.pokemonapp.enums.Nature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "TEAMMEMBER_VIEW")
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberDTO {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "team_id")
    private long teamId;

    @Column(name ="pokemon_id")
    private long pokemonId;

    @Column(name ="valid")
    private boolean valid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pokemon_id", nullable = false, insertable = false, updatable = false)
    private PokemonDTO pokemon;

    @OneToMany(fetch = FetchType.LAZY)
    private List<MoveDTO> moveset;

    @JsonProperty("selected")
    @Column(name ="selected")
    private boolean selected;

    // All stat have current, initial calculated, iv and ev values
    // Information about HP stat
    @JsonProperty("current_hp")
    @Transient
    private int currentHp;

    @Column(name = "max_hp")
    @JsonProperty("max_hp")
    private int maxHp;

    @Column(name = "iv_hp")
    @JsonProperty("iv_hp")
    private int ivHp;

    @Column(name = "ev_hp")
    @JsonProperty("ev_hp")
    private int evHp;

    // Information about Atk stat
    @JsonProperty("current_atk")
    @Transient
    private int currentAtk;

    @Column(name = "calc_atk")
    @JsonProperty("atk")
    private int atk;

    @Column(name = "iv_atk")
    @JsonProperty("iv_atk")
    private int ivAtk;

    @Column(name = "ev_atk")
    @JsonProperty("ev_atk")
    private int evAtk;

    // Information about Def stat
    @JsonProperty("current_def")
    @Transient
    private int currentDef;

    @Column(name = "calc_def")
    @JsonProperty("def")
    private int def;

    @Column(name = "iv_def")
    @JsonProperty("iv_def")
    private int ivDef;

    @Column(name = "ev_def")
    @JsonProperty("ev_def")
    private int evDef;

    // Information about Spa
    @JsonProperty("current_spa")
    @Transient
    private int currentSpa;

    @Column(name = "calc_spa")
    @JsonProperty("spa")
    private int spa;

    @Column(name = "iv_spa")
    @JsonProperty("iv_spa")
    private int ivSpa;

    @Column(name = "ev_spa")
    @JsonProperty("ev_spa")
    private int evSpa;

    // Information about Spd
    @JsonProperty("current_spd")
    @Transient
    private int currentSpd;

    @Column(name = "calc_spd")
    @JsonProperty("spd")
    private int spd;

    @Column(name = "iv_spd")
    @JsonProperty("iv_spd")
    private int ivSpd;

    @Column(name = "ev_spd")
    @JsonProperty("ev_spd")
    private int evSpd;

    // Information about Speed
    @JsonProperty("current_speed")
    @Transient
    private int currentSpeed;

    @Column(name = "calc_speed")
    @JsonProperty("speed")
    private int speed;

    @Column(name = "iv_speed")
    @JsonProperty("iv_speed")
    private int ivSpeed;

    @Column(name = "ev_speed")
    @JsonProperty("ev_speed")
    private int evSpeed;

    @Column(name = "nature_id")
    @JsonProperty("nature_id")
    private long natureId;

    @Column(name = "nature_name")
    @JsonProperty("nature_name")
    @Enumerated(EnumType.STRING)
    private Nature nature;

    @JsonProperty("burned")
    @Transient
    private boolean burned;

    @JsonProperty("fainted")
    @Transient
    private boolean fainted;
}
