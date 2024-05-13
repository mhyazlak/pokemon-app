package com.pkm.pokemonapp.controller;

import com.pkm.pokemonapp.config.CustomPrincipal;
import com.pkm.pokemonapp.enums.MatchDecision;
import com.pkm.pokemonapp.service.impl.MatchmakingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class MatchmakingController {

    @Autowired
    private MatchmakingServiceImpl matchmakingService;

    @MessageMapping("/joinQueue")
    public void joinQueue(CustomPrincipal principal) {
        log.info("User {} has queued up", principal.getUser().getUsername());
        try {
            matchmakingService.addUserToQueue(principal.getUser());
        } catch (Exception e) {
            log.error("Error reaching: /joinQueue", e);
        }

    }

    @MessageMapping("/handlePlayerMatchDecision/{sessionId}")
    public void handlePlayerMatchDecision(CustomPrincipal principal, @Payload MatchDecision matchDecision, @DestinationVariable String sessionId) {

        //log.info("Session ID: {}, {},by {}", sessionId, matchDecision.name(), principal.getName());

        //matchmakingService.handlePlayerMatchDecision(principal.getUser(), matchDecision, sessionId);
    }

}

