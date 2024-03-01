package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.enums.MatchDecision;
import com.pkm.pokemonapp.enums.QueueStatus;
import com.pkm.pokemonapp.model.QueueResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


@Service
@Slf4j
public class MatchmakingService {

    // Queue contains usernames
    private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    // sessionId to map of username and decisions
    // entry created if at least one user accepts the match
    private final ConcurrentHashMap<String, Map<UserDTO, MatchDecision>> potentialSessions = new ConcurrentHashMap<>();

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    BattleService battleService;

    public void addToQueue(UserDTO user) {
        queue.offer(user.getUsername());
        QueueResponse queueResponse = new QueueResponse(QueueStatus.IN_QUEUE, "");
        messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/match", queueResponse);
        checkForMatches();
    }

    public void removeFromQueue(String username) {
        queue.remove(username);
    }

    public void handlePlayerMatchDecision(UserDTO user, MatchDecision matchDecision, String sessionId) {
        if(matchDecision.equals(MatchDecision.DECLINE)){
            QueueResponse queueResponse = new QueueResponse(QueueStatus.MATCH_DECLINED, "");
            for (UserDTO player : potentialSessions.get(sessionId).keySet()) {
                messagingTemplate.convertAndSendToUser(player.getUsername(), "/queue/match", queueResponse);
            }
        } else {
            // check if any user has already accepted, if there is already a item in the map then that is the case
            Map<UserDTO, MatchDecision> playerDecisions = potentialSessions.get(sessionId);
            if (playerDecisions == null) {
               playerDecisions = new HashMap<UserDTO, MatchDecision>();
               playerDecisions.put(user, MatchDecision.ACCEPT);
               potentialSessions.put(sessionId ,playerDecisions);
            }

            if (playerDecisions.get(user) == null && matchDecision.equals(MatchDecision.ACCEPT)) {
                playerDecisions.put(user, matchDecision);
            }
            // check if both players have accepted the match, send a message with the sessionId and notify that the match is starting
            // Create a session in the battleService and handle further actions there
            if (playerDecisions.keySet().size() >= 2) {
                for (UserDTO player : playerDecisions.keySet()) {
                    QueueResponse queueResponse = new QueueResponse(QueueStatus.MATCH_STARTS, sessionId);
                    messagingTemplate.convertAndSendToUser(player.getUsername(), "/queue/match", queueResponse);
                }
                battleService.createBattleSession(sessionId, playerDecisions.keySet());
            }
        }
    }

    private void checkForMatches() {
        if (queue.size() >= 2) {
            String player1 = queue.poll();
            String player2 = queue.poll();
            String sessionId = UUID.randomUUID().toString();

            QueueResponse queueResponse = new QueueResponse(QueueStatus.MATCH_FOUND, sessionId);

            messagingTemplate.convertAndSendToUser(player1, "/queue/match", queueResponse);
            messagingTemplate.convertAndSendToUser(player2, "/queue/match", queueResponse);
        }
    }

}
