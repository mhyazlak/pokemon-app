package com.pkm.pokemonapp.service;

import com.pkm.pokemonapp.dto.PokemonDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPokemonService {

    @Transactional(readOnly = true)
    PokemonDTO read(long id);

    @Transactional(readOnly = true)
    List<PokemonDTO> readAll();
}
