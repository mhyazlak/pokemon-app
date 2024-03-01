package com.pkm.pokemonapp.controller;

import com.pkm.pokemonapp.config.CustomPrincipal;
import com.pkm.pokemonapp.enums.MatchDecision;
import com.pkm.pokemonapp.service.impl.MatchmakingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
//@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class MatchmakingController {

    @Autowired
    private MatchmakingService matchmakingService;

    @MessageMapping("/joinQueue")
    public void joinQueue(CustomPrincipal principal) {
        matchmakingService.addToQueue(principal.getUser());
    }

    @MessageMapping("/acceptMatch/{sessionId}")
    public void acceptMatch(CustomPrincipal principal, @Payload MatchDecision matchDecision, @DestinationVariable String sessionId) {

        log.info("Session ID: {}", sessionId);
        log.info("Match decision: {}", matchDecision.name());

        matchmakingService.handlePlayerMatchDecision(principal.getUser(), matchDecision, sessionId);
    }

}

