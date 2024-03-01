package com.pkm.pokemonapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private long id;

    @Column(name = "user_id")
    @JsonProperty("user_id")
    private long userId;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name ="selected")
    @JsonProperty("selected")
    private boolean selected;

    @Transient
    private List<TeamMemberDTO> members = new ArrayList<TeamMemberDTO>(){};
}
