package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.TeamMemberDAO;
import com.pkm.pokemonapp.dto.MoveDTO;
import com.pkm.pokemonapp.dto.PokemonDTO;
import com.pkm.pokemonapp.dto.TeamMemberDTO;
import com.pkm.pokemonapp.enums.Nature;
import com.pkm.pokemonapp.service.ITeamMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
    public long addMember(long teamId, TeamMemberDTO memberDTO) {
        // TODO get the values from frontend

        PokemonDTO pokemon = memberDTO.getPokemon();

        memberDTO.setEvHp(0);
        memberDTO.setEvAtk(0);
        memberDTO.setEvDef(0);
        memberDTO.setEvSpa(0);
        memberDTO.setEvSpd(0);
        memberDTO.setEvSpeed(0);
        memberDTO.setPokemonId(pokemon.getId());

        memberDTO.setIvHp(0);
        memberDTO.setIvAtk(0);
        memberDTO.setIvDef(0);
        memberDTO.setIvSpa(0);
        memberDTO.setIvSpd(0);
        memberDTO.setIvSpeed(0);

        // TODO take nature from user instead of default
        memberDTO.setNature(Nature.ADAMANT);
        memberDTO.setNatureId(Nature.ADAMANT.getId());

        return teamMemberDAO.create(
                teamId, memberDTO.getPokemonId(), memberDTO.getIvHp(), memberDTO.getEvHp(),
                memberDTO.getIvAtk(), memberDTO.getEvAtk(), memberDTO.getIvDef() ,memberDTO.getEvDef(),
                memberDTO.getIvSpa(), memberDTO.getEvSpa(), memberDTO.getIvSpd(), memberDTO.getEvSpd(),
                memberDTO.getIvSpeed(), memberDTO.getEvSpeed(), memberDTO.getNatureId());
    }


    @Override
    public void updateMoveset(long memberId) {
        teamMemberDAO.updateMoveset(memberId);
    }

}
