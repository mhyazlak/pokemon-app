package com.pkm.pokemonapp.service;

import com.pkm.pokemonapp.dto.TeamDTO;
import com.pkm.pokemonapp.dto.UserDTO;

import java.util.List;

public interface ITeamService {

    /**
     * create a blank team to further edit
     *
     * @param team blank team with name
     * @param user that is creating the team
     * @return id of newly created team
     */
    public long createTeam(TeamDTO team, UserDTO user);

    /**
     * reads all non deleted teams for user
     *
     * @param user that is creating the team
     * @return list of teams
     */
    public List<TeamDTO> readTeams(UserDTO user);

    /**
     * sets the selected flag for a team to true,
     * sets the flag to other teams to false
     *
     * @param teamId that is creating the team
     * @param user that is creating the team
     * @return team that is being flagged for selected
     */
    public TeamDTO selectTeam(long teamId, UserDTO user);

    /**
     * takes in team and overrides current config for each team member
     *
     * @param team to override the config for
     * @param user that is editing
     */
    public void saveTeamConfig(TeamDTO team, UserDTO user);

    /**
     * gets the currently selected Team for a match
     *
     * @param userId id of the user that is starting a match
     */
    public TeamDTO getSelectedTeam(long userId);
}
