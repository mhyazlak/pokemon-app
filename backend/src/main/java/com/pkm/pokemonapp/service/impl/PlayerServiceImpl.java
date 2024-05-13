package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dto.TeamDTO;
import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlayerServiceImpl {

    @Autowired
    private TeamServiceImpl teamService;

    public Player createPlayer(UserDTO user) {
        TeamDTO team = teamService.getSelectedTeam(user.getId());
        return new Player(user, team);
    }
}
