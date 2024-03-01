package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.model.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BattleService {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    PlayerServiceImpl playerService;

    private final ConcurrentHashMap<String, GameState> activeSessions = new ConcurrentHashMap<>();

    public void createBattleSession(String sessionId, Set<UserDTO> players) {
        if (players == null || players.size() != 2) {
            throw new IllegalArgumentException("Players set must contain exactly two usernames.");
        }

        Iterator<UserDTO> iterator = players.iterator();
        UserDTO user1 = iterator.next();
        UserDTO user2 = iterator.next();

        GameState initGameState = new GameState();

        // TODO Send individual Gamestate depending on player, hide stat info for enemy

        initGameState.addPlayer(playerService.createPlayer(user1));
        initGameState.addPlayer(playerService.createPlayer(user2));

        activeSessions.put(sessionId, initGameState);


        messagingTemplate.convertAndSendToUser(user1.getUsername(), "/battle", initGameState);
        messagingTemplate.convertAndSendToUser(user2.getUsername(), "/battle", initGameState);
    }


    public void removeBattleSession(String sessionId) {
        // Remove the session when the battle is over or aborted
        activeSessions.remove(sessionId);
    }

    // Additional methods to manage and interact with the sessions can be added here
}
