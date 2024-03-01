package com.pkm.pokemonapp.event;

import com.pkm.pokemonapp.service.impl.MatchmakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class WebSocketEventListener{

    @Autowired
    private  SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private  MatchmakingService matchmakingService; // Your matchmaking service



   @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String username = event.getUser().getName();
        matchmakingService.removeFromQueue(username);
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

    }

}
