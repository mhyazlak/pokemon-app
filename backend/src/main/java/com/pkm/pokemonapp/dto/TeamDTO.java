package com.pkm.pokemonapp.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Team")
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "name")
    private String name;

    @Transient
    private List<TeamMemberDTO> members = new ArrayList<TeamMemberDTO>(){};
}
