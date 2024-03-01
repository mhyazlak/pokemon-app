package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.MoveDAO;
import com.pkm.pokemonapp.dto.MoveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveServiceImpl {

    @Autowired
    private MoveDAO moveDAO;

    public List<MoveDTO> getMoveset(long memberId) {
        return moveDAO.readMoveset(memberId);
    }

}
