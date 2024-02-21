package com.pkm.pokemonapp.controller;


import com.pkm.pokemonapp.service.IPokemonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
public class MainController {


    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}
