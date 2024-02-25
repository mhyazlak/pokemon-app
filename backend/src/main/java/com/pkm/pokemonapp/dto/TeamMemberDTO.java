package com.pkm.pokemonapp.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "TeamMember")
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


}
