package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.TeamDAO;
import com.pkm.pokemonapp.dto.*;
import com.pkm.pokemonapp.service.ITeamMemberService;
import com.pkm.pokemonapp.service.ITeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
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
                long newMemberId = memberService.addMember(team.getId(), newMember);
                updateMoveset(newMemberId, newMember.getMoveset());
            }
        }
        if (!currentTeam.getName().equals(team.getName())) {
            currentTeam.setName(team.getName());
        }
    }

    @Override
    public TeamDTO getSelectedTeam(long userId) {
        TeamDTO team = teamDAO.getSelectedTeam(userId);
        List<TeamMemberDTO> members = memberService.readTeamMembers(team.getId());

        // this team is used for the intial gamestate at the start of the match
        // deliver the members with actual live stats after calculating, set the values for stats and the live stats
        for (TeamMemberDTO member : members) {
            setInitialStats(member);
        }

        team.setMembers(members);
        return team;
    }

    private void setInitialStats(TeamMemberDTO member) {
        // setting initial current stats
        // stats are being calculated in db when inserting a teammember
        member.setCurrentHp(member.getMaxHp());
        member.setCurrentAtk(member.getAtk());
        member.setCurrentDef(member.getDef());
        member.setCurrentSpa(member.getSpa());
        member.setCurrentSpd(member.getSpd());
        member.setCurrentSpeed(member.getSpeed());
    }

    private void updateMoveset(long newMemberId, List<MoveDTO> moveset) {
        memberService.updateMoveset(newMemberId);
    }

}
