package com.pkm.pokemonapp.dao;

import com.pkm.pokemonapp.dto.PokemonDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PokemonDAO {


    private static final String SELECT_POKEMON_BY_ID = "SELECT * FROM POKEMON_VIEW where id = ?1";
    private static final String SELECT_ALL_POKEMON ="SELECT * FROM POKEMON_VIEW";

    @Autowired
    EntityManager entityManager;

    public PokemonDTO read(long id) {
        StringBuilder sb = new StringBuilder(SELECT_POKEMON_BY_ID);
        Query query = entityManager.createNativeQuery(sb.toString(), PokemonDTO.class);
        query.setParameter(1, id);
        return (PokemonDTO) query.getSingleResult();
    }


    public List<PokemonDTO> readAll() {
        StringBuilder sb = new StringBuilder(SELECT_ALL_POKEMON);
        Query query = entityManager.createNativeQuery(sb.toString(), PokemonDTO.class);
        return (List<PokemonDTO>) query.getResultList();
    }

}
