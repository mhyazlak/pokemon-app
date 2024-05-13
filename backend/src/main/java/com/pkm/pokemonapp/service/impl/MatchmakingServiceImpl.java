package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.enums.MatchDecision;
import com.pkm.pokemonapp.enums.QueueStatus;
import com.pkm.pokemonapp.model.QueueResponse;
import com.pkm.pokemonapp.service.IMatchmakkingService;
import com.pkm.pokemonapp.service.exception.MatchMakingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


@Service
@Slf4j
public class MatchmakingServiceImpl implements IMatchmakkingService {
    // Queue contains usernames
    private final ConcurrentLinkedQueue<UserDTO> playerQueue = new ConcurrentLinkedQueue<>();
    // sessionId to map of username and decisions
    // entry created if at least one user accepts the match
    private final ConcurrentHashMap<String, Map<UserDTO, MatchDecision>> pendingSessions = new ConcurrentHashMap<>();

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    BattleServiceImpl battleService;

    public void addUserToQueue(UserDTO user) {
        // Check if the user is already in queue
        if (playerQueue.contains(user)) {
            throw new MatchMakingException("already in queue");
        }
        // Check if the user is already in an ongoing match
        if (battleService.checkPlayerInSession(user)) {
            throw new MatchMakingException("already in match");
        }
        // Add user to queue
        playerQueue.offer(user);
        // Response to handle on frontend
        QueueResponse queueResponse = new QueueResponse(QueueStatus.IN_QUEUE, "");
        // send to queue endpoint
        messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/", queueResponse);
        // check if there is a match, send sessionId to the respective users
        checkForMatches();
    }

    public void removeUserFromQueue(UserDTO user) {
        playerQueue.remove(user);
    }

    public void handlePlayerMatchDecision(UserDTO user, MatchDecision matchDecision, String sessionId) {
        // If player declines, remove player from queue
        // Also put the user from pending session back in queue
        // remove that potential session
        // TODO currently only one player need to Accept for the match to begin, this is a bug
        if (matchDecision.equals(MatchDecision.DECLINE)) {
            QueueResponse queueResponse = new QueueResponse(QueueStatus.MATCH_DECLINED, "");
            for (UserDTO player : pendingSessions.get(sessionId).keySet()) {

                // only send the other user the decline status, the declining user will already be out
                if (!player.equals(user)) {
                    messagingTemplate.convertAndSendToUser(player.getUsername(), "/queue/", queueResponse);
                    pendingSessions.remove(sessionId);
                    addUserToQueue(user);
                }
            }
        } else {
            // In case the user send a ACCEPT decision we put it in the pendingSessions and then check
            // if both players have accepted
            Map<UserDTO, MatchDecision> playerDecisions = pendingSessions.get(sessionId);

            if (playerDecisions.get(user) == null && matchDecision.equals(MatchDecision.ACCEPT)) {
                playerDecisions.put(user, matchDecision);
            }
            // check if both players have accepted the match, send a message with the sessionId and notify that the match is starting
            // Create a session in the battleService and handle further actions there
            if (playerDecisions.keySet().size() >= 2) {
                for (UserDTO player : playerDecisions.keySet()) {
                    QueueResponse queueResponse = new QueueResponse(QueueStatus.MATCH_STARTS, sessionId);
                    messagingTemplate.convertAndSendToUser(player.getUsername(), "/queue/", queueResponse);
                }
                battleService.createBattleSession(sessionId, playerDecisions.keySet());
            }
        }
    }

    // Checks if matching criteria is met
    // TODO Currently no criteria is specified
    private void checkForMatches() {
        // Ensure there are at least two players in the queue
        while (playerQueue.size() >= 2) {

            String sessionId = UUID.randomUUID().toString();  // Generate a unique session ID for the match
            pendingSessions.put(sessionId, new HashMap<>());
            Map<UserDTO, MatchDecision> pendingSession = pendingSessions.get(sessionId);

            for (int i = 0; i < 2; i++) {
                UserDTO player = playerQueue.poll();
                pendingSession.put(player, MatchDecision.PENDING);
                QueueResponse queueResponse = new QueueResponse(QueueStatus.MATCH_FOUND, sessionId);
                // Notify the player that a match has been found, sending them the session ID
                messagingTemplate.convertAndSendToUser(player.getUsername(), "/queue/", queueResponse);
            }
        }
    }

}
