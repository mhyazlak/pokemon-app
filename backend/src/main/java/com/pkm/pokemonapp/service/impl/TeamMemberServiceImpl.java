package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.TeamMemberDAO;
import com.pkm.pokemonapp.dto.TeamMemberDTO;
import com.pkm.pokemonapp.service.ITeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberServiceImpl implements ITeamMemberService {

    @Autowired
    TeamMemberDAO teamMemberDAO;

    @Override
    public List<TeamMemberDTO> readTeamMembers(long teamId) {
        List<TeamMemberDTO> members = teamMemberDAO.readTeamMembers(teamId);
        return members;
    }

    @Override
    public void updateMember(TeamMemberDTO teamMemberDTO) {

    }

    @Override
    public void addMember(long teamId, long pokemonId) {
        teamMemberDAO.create(teamId, pokemonId);
    }
}
