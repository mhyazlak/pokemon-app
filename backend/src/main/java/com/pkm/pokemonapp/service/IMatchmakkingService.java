package com.pkm.pokemonapp.service;

import com.pkm.pokemonapp.dto.UserDTO;

public interface IMatchmakkingService {

    /**
     * puts a user into the queue
     *
     * @param user the user who wants to join the queue
     */
    public void addUserToQueue(UserDTO user);

    /**
     * removes given user from the Queue
     *
     * @param user a user who will be removed from queue
     *
     */
    public void removeUserFromQueue(UserDTO user);


}
