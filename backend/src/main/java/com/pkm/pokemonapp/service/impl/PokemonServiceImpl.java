package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.PokemonDAO;
import com.pkm.pokemonapp.dto.PokemonDTO;
import com.pkm.pokemonapp.service.IPokemonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PokemonServiceImpl implements IPokemonService {

    @Autowired
    PokemonDAO pokemonDao;

    @Override
    public PokemonDTO read(long id) {
        return pokemonDao.read(id);
    }

    @Override
    public List<PokemonDTO> readAll() {
        return pokemonDao.readAll();
    }
}
