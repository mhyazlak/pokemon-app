package com.pkm.pokemonapp.dao;

import com.pkm.pokemonapp.dto.TeamMemberDTO;
import com.pkm.pokemonapp.model.GameRules;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TeamMemberDAO {

    private static final String READ_TEAM_MEMBERS = "SELECT * FROM TEAMMEMBER_VIEW WHERE team_id = ?1";
    private static final String CREATE_TEAM_MEMBER = "CALL CREATE_TEAMMEMBER(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16)";

    private static final String UPDATE_MOVESET ="INSERT INTO `Moveset` (member_id, move_id) Values (?1, 1)"; // TODO Stored Procedure for UPDATE_MOVESET

    @PersistenceContext
    EntityManager entityManager;

    public List<TeamMemberDTO> readTeamMembers(long teamId) {
        Query query = entityManager.createNativeQuery(READ_TEAM_MEMBERS, TeamMemberDTO.class);
        query.setParameter(1, teamId);

        return (List<TeamMemberDTO>) query.getResultList();
    }

    public long create(long teamId, long pokemonId, int ivHp, int evHp, int ivAtk, int evAtk, int ivDef, int evDef,
                       int ivSpa, int evSpa, int ivSpd, int evSpd, int ivSpeed, int evSpeed, long natureId) {
        Query query = entityManager.createNativeQuery(CREATE_TEAM_MEMBER);
        query.setParameter(1, teamId);
        query.setParameter(2, pokemonId);
        query.setParameter(3, GameRules.LEVEL); // Constant for the level
        query.setParameter(4, ivHp);
        query.setParameter(5, evHp);
        query.setParameter(6, ivAtk);
        query.setParameter(7, evAtk);
        query.setParameter(8, ivDef);
        query.setParameter(9, evDef);
        query.setParameter(10, ivSpa);
        query.setParameter(11, evSpa);
        query.setParameter(12, ivSpd);
        query.setParameter(13, evSpd);
        query.setParameter(14, ivSpeed);
        query.setParameter(15, evSpeed);
        query.setParameter(16, natureId);

            return (long) query.getSingleResult();
    }

    public void updateMoveset(long newMemberId) {
        Query query = entityManager.createNativeQuery(UPDATE_MOVESET);
        query.setParameter(1, newMemberId);
        query.executeUpdate();
    }
}
