package com.pkm.pokemonapp.model;

import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthorizedUser implements Serializable {

    private static final long serialVersionUID = -3693969868639612824L;
    private UserDTO user;
    private Role role;

}