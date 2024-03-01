package com.pkm.pokemonapp.service;

import com.pkm.pokemonapp.dto.TeamDTO;
import com.pkm.pokemonapp.dto.UserDTO;

import java.util.List;

public interface ITeamService {

    public long createTeam(TeamDTO team, UserDTO user);

    public List<TeamDTO> readTeams(UserDTO user);

    public TeamDTO selectTeam(long teamId, UserDTO user);

    public void saveTeamConfig(TeamDTO team, UserDTO user);

    public TeamDTO getSelectedTeam(long userId);
}
