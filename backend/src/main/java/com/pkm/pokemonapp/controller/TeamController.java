package com.pkm.pokemonapp.controller;

import com.pkm.pokemonapp.annotation.AccessRole;
import com.pkm.pokemonapp.dto.TeamDTO;
import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.model.AjaxResponse;
import com.pkm.pokemonapp.model.AuthorizedUser;
import com.pkm.pokemonapp.service.ITeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.pkm.pokemonapp.enums.Role;

import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/api/team")
@Slf4j
public class TeamController {

    @Autowired
    ITeamService teamService;

    @PostMapping("/create")
    public AjaxResponse createTeam(@RequestBody TeamDTO teamDTO, UserDTO user){
        log.info("requesting URL: /api/team/create");
        try {
            log.info("New Team creation try {} by", user.getUsername());
            long teamId = teamService.createTeam(teamDTO, user);
            return new AjaxResponse(true,teamId,1);
        } catch (Exception e) {
            log.error("Error requesting URL: /team/create", e);
            return new AjaxResponse(false);
        }
    }

    @GetMapping("/readTeams")
    public AjaxResponse readTeams(UserDTO user){
        log.debug("requesting URL: /api/team/readTeams");
        try {
            List<TeamDTO> teams = teamService.readTeams(user);
            return new AjaxResponse(true, teams, teams.size());
        } catch (Exception e) {
            log.error("Error requesting URL: /team/create", e);
            return new AjaxResponse(false);
        }
    }

    @GetMapping("selectTeam")
    public AjaxResponse selectTeam(@RequestParam long teamId, UserDTO user) {
        log.debug("requesting URL: /api/team/selectTeam");
        try {
            TeamDTO team = teamService.selectTeam(teamId, user);
            return new AjaxResponse(true, team, 1);
        } catch (Exception e) {
            log.error("Error requesting URL: /team/selectTeam", e);
            return new AjaxResponse(false);
        }
    }

    @PostMapping("saveTeamConfig")
    public AjaxResponse saveTeamConfig(@RequestBody TeamDTO team, UserDTO user) {
        log.debug("requesting URL: /api/team/saveTeamConfig");
        try {
            teamService.saveTeamConfig(team, user);
            return new AjaxResponse(true);
        } catch (Exception e) {
            log.error("Error requesting URL: /team/saveTeamConfig", e);
            return new AjaxResponse(false);
        }
    }

}
