package com.pkm.pokemonapp.dao;

import com.pkm.pokemonapp.dto.TeamMemberDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TeamMemberDAO {

    private static final String READ_TEAM_MEMBERS = "SELECT * FROM TeamMember WHERE team_id = ?1";

    private static final String CREATE_TEAM_MEMBER = "INSERT INTO TeamMember (team_id, pokemon_id, valid) Values(?1, ?2, 1)";

    @PersistenceContext
    EntityManager entityManager;


    public List<TeamMemberDTO> readTeamMembers(long teamId) {
        Query query = entityManager.createNativeQuery(READ_TEAM_MEMBERS, TeamMemberDTO.class);
        query.setParameter(1, teamId);

        return (List<TeamMemberDTO>) query.getResultList();

    }

    public void create(long teamId, long pokemonId) {
        Query query = entityManager.createNativeQuery(CREATE_TEAM_MEMBER);
        query.setParameter(1, teamId);
        query.setParameter(2, pokemonId);
        query.executeUpdate();
    }
}
