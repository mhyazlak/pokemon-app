package com.pkm.pokemonapp.event;

import com.pkm.pokemonapp.config.CustomPrincipal;
import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.service.impl.CustomUserDetailService;
import com.pkm.pokemonapp.service.impl.MatchmakingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private MatchmakingServiceImpl matchmakingService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Principal principal = event.getUser();
        if (principal instanceof CustomPrincipal customPrincipal) {
            UserDTO user = customPrincipal.getUser();
            matchmakingService.removeUserFromQueue(user);
        }
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

    }

}
