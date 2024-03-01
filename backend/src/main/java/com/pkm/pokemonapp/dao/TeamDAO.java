package com.pkm.pokemonapp.dao;

import com.pkm.pokemonapp.dto.TeamDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamDAO {

    private static final String SELECT_TEAM = "SELECT * FROM Team WHERE id = ?1";
    private static final String SELECT_TEAMLIST = "SELECT * FROM Team WHERE user_id = ?1";
    private static final String READ_SELECTED_TEAM = "SELECT * FROM Team WHERE user_id = ?1 and selected = 1";

    private static final String CREATE_TEAM = "CALL CREATE_TEAM(?1, ?2)";

    @PersistenceContext
    EntityManager entityManager;

    public long createNewTeam(String name,long userId) {
        Query query = entityManager.createNativeQuery(CREATE_TEAM);
        query.setParameter(1, name);
        query.setParameter(2, userId);
        return (long) query.getSingleResult();
    }

    public List<TeamDTO> readTeams(long userId) {
        Query query = entityManager.createNativeQuery(SELECT_TEAMLIST, TeamDTO.class);
        query.setParameter(1, userId);
        return (List<TeamDTO>) query.getResultList();
    }

    public TeamDTO selectTeam(long teamId) {
        Query query = entityManager.createNativeQuery(SELECT_TEAM, TeamDTO.class);
        query.setParameter(1, teamId);
        return (TeamDTO) query.getSingleResult();
    }

    public TeamDTO getSelectedTeam(long userId) {
        Query query = entityManager.createNativeQuery(READ_SELECTED_TEAM, TeamDTO.class);
        query.setParameter(1, userId);
        return (TeamDTO) query.getSingleResult();
    }
}
