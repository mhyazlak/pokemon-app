package com.pkm.pokemonapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    private String password;

    public UserDTO (long id, String username) {
        this.id = id;
        this.username = username;
    }

}