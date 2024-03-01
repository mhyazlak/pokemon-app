package com.pkm.pokemonapp.controller;

import com.pkm.pokemonapp.dto.LoginDTO;
import com.pkm.pokemonapp.dto.RegistrationDTO;
import com.pkm.pokemonapp.model.AjaxResponse;
import com.pkm.pokemonapp.service.impl.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public AjaxResponse register(@RequestBody RegistrationDTO body){
        log.info("requesting URL: /auth/register");
        try {
            log.info("New Registration Try {}", body.getUsername());
            authenticationService.registerUser(body.getUsername(),body.getPassword());
            return new AjaxResponse(true);
        } catch (Exception e) {
            log.error("Error requesting URL: /auth/register", e);
            return new AjaxResponse(false);
        }
    }

    @PostMapping("/login")
    public AjaxResponse login (@RequestBody LoginDTO body){
        log.info("requesting URL: /auth/login, user {} tries to login", body.getUsername());
        try {
            String token = authenticationService.loginUser(body.getUsername(), body.getPassword());
            log.info("User {} successfully logged in", body.getUsername());
            return new AjaxResponse(true, token,0);
        } catch (Exception e) {
            log.error("Error requesting URL: /auth/login", e);
        }
        return null;
    }

}
