package com.pkm.pokemonapp.dao;

import com.pkm.pokemonapp.dto.MoveDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MoveDAO {

    private static final String MOVES_FOR_MEMBER = "SELECT move_id as id, name as name, `type`, `power`, `accuracy` FROM MOVESET_VIEW WHERE member_id = ?1";

    @PersistenceContext
    EntityManager entityManager;

    public List<MoveDTO> readMoveset(long memberId) {
        Query query = entityManager.createNativeQuery(MOVES_FOR_MEMBER, MoveDTO.class);
        query.setParameter(1, memberId);
        return (List<MoveDTO>) query.getResultList();
    }


}
