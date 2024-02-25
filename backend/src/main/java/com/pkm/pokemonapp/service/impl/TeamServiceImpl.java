package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.TeamDAO;
import com.pkm.pokemonapp.dto.TeamDTO;
import com.pkm.pokemonapp.dto.TeamMemberDTO;
import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.service.ITeamMemberService;
import com.pkm.pokemonapp.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements ITeamService {

    @Autowired
    TeamDAO teamDAO;

    @Autowired
    ITeamMemberService memberService;

    @Override
    @Transactional
    public long createTeam(TeamDTO team, UserDTO user) {
        return teamDAO.createNewTeam( team.getName(), user.getId());
    }

    @Override
    public List<TeamDTO> readTeams(UserDTO user) {
        return teamDAO.readTeams(user.getId());
    }

    @Override
    public TeamDTO selectTeam(long teamId, UserDTO user) {
        TeamDTO team = teamDAO.selectTeam(teamId);
        List<TeamMemberDTO> members = memberService.readTeamMembers(teamId);
        team.setMembers(members);
        return team;
    }

    @Override
    @Transactional
    public void saveTeamConfig(TeamDTO team, UserDTO user) {
        // Select the current team configuration from the database
        TeamDTO currentTeam = selectTeam(team.getId(), user);

        // Get the current team members from the database
        List<TeamMemberDTO> currentMembers = currentTeam.getMembers();

        // Convert currentMembers to a map by their pokemonId for quick lookup
        Map<Long, TeamMemberDTO> currentMemberMap = currentMembers.stream()
                .collect(Collectors.toMap(member -> member.getPokemon().getId(), member -> member));

        // Loop through the new team members and add any that are not in the current team
        for (TeamMemberDTO newMember : team.getMembers()) {
            if (!currentMemberMap.containsKey(newMember.getPokemon().getId())) {
                // This is a new member, so add it using the member service
                memberService.addMember(team.getId(), newMember.getPokemon().getId());
            }
        }
        // TODO
        // Optionally, you can also check for members that need to be removed
        // if the team members can also be removed in the new configuration.
        // If so, you would also loop through the current members and remove
        // any that are not present in the new team.

        // After adding new members (and potentially removing old ones), you may want
        // to update the rest of the team information as needed and save the changes.
        // This could involve updating team details and persisting them back to the database.

        // Example: Update team name if it has changed
        if (!currentTeam.getName().equals(team.getName())) {
            currentTeam.setName(team.getName());
            //teamDAO.save(currentTeam);
        }

        // Save other changes to team as necessary...
    }

}
