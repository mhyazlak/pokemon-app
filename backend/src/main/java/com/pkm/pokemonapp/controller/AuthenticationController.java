package com.pkm.pokemonapp.controller;

import com.pkm.pokemonapp.dto.LoginResponseDTO;
import com.pkm.pokemonapp.dto.RegistrationDTO;
import com.pkm.pokemonapp.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public void registerUser(@RequestBody RegistrationDTO body){
        authenticationService.registerUser(body.getUsername(),body.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser (@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }

}
