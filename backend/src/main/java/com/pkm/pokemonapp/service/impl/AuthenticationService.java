package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.UserDAO;
import com.pkm.pokemonapp.dto.LoginResponseDTO;
import com.pkm.pokemonapp.enums.Role;
import com.pkm.pokemonapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserDAO userDAO;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UserDTO registerUser(String username, String password) {

        String encodePassword = passwordEncoder.encode((password));
        Set<Role> authorities = new HashSet<>();
        authorities.add(Role.USER);

        userDAO.createNewUser(username, encodePassword);

        return null;
    }

    public LoginResponseDTO loginUser(String username, String password) {

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            String token = tokenService.generateJwt(authentication);
            UserDTO user = userDAO.readByUserName(username);
            return new LoginResponseDTO(user, token);
        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }
    }
}
