package com.pkm.pokemonapp.controller;

import com.pkm.pokemonapp.annotation.AccessRole;
import com.pkm.pokemonapp.enums.Role;
import com.pkm.pokemonapp.model.AuthorizedUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @GetMapping("/")
    @AccessRole(roles = Role.ADMIN)
    public String helloUserConroller(AuthorizedUser user){
        System.out.println(user);
        return "UserDTO access level";
    }

}
