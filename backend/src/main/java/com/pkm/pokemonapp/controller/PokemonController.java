package com.pkm.pokemonapp.controller;

import com.pkm.pokemonapp.dto.PokemonDTO;
import com.pkm.pokemonapp.enums.Type;
import com.pkm.pokemonapp.model.AjaxResponse;
import com.pkm.pokemonapp.service.IPokemonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    @Autowired
    IPokemonService pokemonService;

    @ResponseBody
    @GetMapping("/read")
    public AjaxResponse read(@RequestParam long id) {
        try {
            PokemonDTO data = pokemonService.read(id);
            return new AjaxResponse(true, data,1);
        } catch (Exception e) {
            log.error("Error reaching: /api/pokemon/read");
            return new AjaxResponse(false, e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/readAll")
    public AjaxResponse readAll(@RequestParam(required = false) String search,
                                @RequestParam(required = false) String sort,
                                @RequestParam(required = false) Type[] types) {
        try {

            List<PokemonDTO> data = pokemonService.readAll();
            return new AjaxResponse(true, data, data.size());
        } catch (Exception e) {
            log.info("Error reaching: /api/pokemon/readAll");
            return new AjaxResponse(false, e.getMessage());
        }
    }
}
