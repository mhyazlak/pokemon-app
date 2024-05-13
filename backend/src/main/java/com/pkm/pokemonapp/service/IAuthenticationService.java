package com.pkm.pokemonapp.service;

import com.pkm.pokemonapp.dto.LoginDTO;
import com.pkm.pokemonapp.dto.RegistrationDTO;
import com.pkm.pokemonapp.service.exception.RegistrationException;

public interface IAuthenticationService {

    /**
     * Authenticates a user based on the username and password provided.
     *
     * @param login the login info of the user
     * @return a jwt token that can be used for authenticated subsequent requests
     */
    String loginUser(LoginDTO login);

    /**
     * Registers a new user with the specified username and password.
     *
     * @param registration the registration info for the new user
     */
    void registerUser(RegistrationDTO registration) throws RegistrationException;


    /**
     * Validates the provided authentication token.
     *
     * @param token the authentication token to validate
     * @return a string indicating the authentication status
     */
    String authenticateToken(String token);
}
