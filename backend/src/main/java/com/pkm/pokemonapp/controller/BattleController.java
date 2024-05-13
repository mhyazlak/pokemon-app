package com.pkm.pokemonapp.controller;


import com.pkm.pokemonapp.config.CustomPrincipal;
import com.pkm.pokemonapp.model.PlayerAction;
import com.pkm.pokemonapp.service.IBattleService;
import com.pkm.pokemonapp.service.exception.InvalidPlayerActionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class BattleController {

    @Autowired
    private IBattleService battleService;

    @MessageMapping("/battle/{sessionId}/action")
    public void handlePlayerAction(@DestinationVariable String sessionId, CustomPrincipal principal, @Payload PlayerAction action) {
        try {
            log.info(principal.getName());
            log.info(action.getActionType().toString());
            battleService.handlePlayerAction(action, sessionId, principal.getUser());
        } catch (
                InvalidPlayerActionException e) {
            log.error("/battle/{sessionId}/action", e);
        }
    }
}
