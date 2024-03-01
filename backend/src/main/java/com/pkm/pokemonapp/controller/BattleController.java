package com.pkm.pokemonapp.controller;


import com.pkm.pokemonapp.model.PlayerAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class BattleController {
    @MessageMapping("/battle/{sessionId}/action")
    public void handlePlayerAction(@DestinationVariable String sessionId, Principal principal, @Payload PlayerAction action) {
        log.info(principal.getName());
        log.info(action.getActionType().toString());
    }

}
