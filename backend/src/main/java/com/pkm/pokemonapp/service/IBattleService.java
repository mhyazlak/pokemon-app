package com.pkm.pokemonapp.service;

import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.model.PlayerAction;

import java.util.Set;

public interface IBattleService {

    /**
     * create the session for match
     *
     * @param sessionId the sessionId that was distributed when players matched up
     * @param players players that will participate in match
     */
    public void createBattleSession(String sessionId, Set<UserDTO> players);

    public void removeBattleSession(String sessionId);

    /**
     * checks if given username is currently in an ongoing match
     *
     * @param user player to check
     * @return true if player is currently in an active session
     */
    boolean checkPlayerInSession(UserDTO user);

    void handlePlayerAction(PlayerAction action, String sessionId, UserDTO user);
}
