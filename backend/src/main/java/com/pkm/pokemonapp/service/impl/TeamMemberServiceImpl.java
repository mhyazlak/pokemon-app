package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.TeamMemberDAO;
import com.pkm.pokemonapp.dto.MoveDTO;
import com.pkm.pokemonapp.dto.TeamMemberDTO;
import com.pkm.pokemonapp.service.ITeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberServiceImpl implements ITeamMemberService {

    @Autowired
    TeamMemberDAO teamMemberDAO;

    @Autowired
    MoveServiceImpl moveService;

    @Override
    public List<TeamMemberDTO> readTeamMembers(long teamId) {
        List<TeamMemberDTO> members = teamMemberDAO.readTeamMembers(teamId);

        // get Moveset for each member
        for (TeamMemberDTO member : members) {
            List<MoveDTO> moveset = moveService.getMoveset(member.getId());
            member.setMoveset(moveset);
        }

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
