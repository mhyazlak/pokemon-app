package com.pkm.pokemonapp.controller;

import com.pkm.pokemonapp.dto.LoginDTO;
import com.pkm.pokemonapp.dto.RegistrationDTO;
import com.pkm.pokemonapp.model.AjaxResponse;
import com.pkm.pokemonapp.service.IAuthenticationService;
import com.pkm.pokemonapp.service.exception.RegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/register")
    public AjaxResponse register(@RequestBody RegistrationDTO registration){
        log.info("requesting URL: /auth/register");
        try {
            log.info("New Registration Try {}", registration);
            authenticationService.registerUser(registration);
            return new AjaxResponse(true);
        } catch (RegistrationException e) {
            log.error("Error requesting URL: /auth/register", e);
            return new AjaxResponse(false, e.getErrorList().toString());
        } catch (Exception e) {
            log.error("Error requesting URL: /auth/register", e);
            return new AjaxResponse(false);
        }
    }

    @PostMapping("/login")
    public AjaxResponse login (@RequestBody LoginDTO login){
        log.info("requesting URL: /auth/login, user {} tries to login", login.getUsername());
        try {
            String token = authenticationService.loginUser(login);
            log.info("User {} successfully logged in", login.getUsername());
            return new AjaxResponse(true, token,0);
        } catch (Exception e) {
            log.error("Error requesting URL: /auth/login", e);
            return new AjaxResponse(false);
        }
    }

}
