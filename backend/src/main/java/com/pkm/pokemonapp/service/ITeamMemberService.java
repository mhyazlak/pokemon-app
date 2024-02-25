package com.pkm.pokemonapp.service;

import com.pkm.pokemonapp.dto.TeamMemberDTO;

import java.util.List;

public interface ITeamMemberService {

    public List<TeamMemberDTO> readTeamMembers(long teamId);

    public void updateMember(TeamMemberDTO teamMemberDTO);

    void addMember(long teamId, long pokemonId);
}
