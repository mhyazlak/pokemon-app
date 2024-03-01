package com.pkm.pokemonapp.config;

import com.pkm.pokemonapp.dto.UserDTO;

import java.security.Principal;

public class CustomPrincipal implements Principal {
    private UserDTO user;

    public CustomPrincipal(UserDTO user) {
        this.user = user;
    }

    @Override
    public String getName() {
        // Return a unique identifier here, usually the username
        return user.getUsername();
    }

    public UserDTO getUser() {
        return user;
    }
}
