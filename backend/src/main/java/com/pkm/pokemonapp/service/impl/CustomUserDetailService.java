package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.UserDAO;
import com.pkm.pokemonapp.dto.AuthorityDTO;
import com.pkm.pokemonapp.dto.PermissionDTO;
import com.pkm.pokemonapp.enums.Role;
import com.pkm.pokemonapp.model.AuthorizedUser;
import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.model.UserRoles;
import com.pkm.pokemonapp.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        AuthorizedUser authorizedUser = null;
        if (!"unknown".equalsIgnoreCase(username)) {
            UserDTO user = userDAO.readByUserName(username);
            List<Role> roles = new ArrayList<>();
            final PermissionDTO permission = userDAO.getUserPermission(user.getId());
            if (permission != null) {
                // Map the permission bits to user roles
                roles = mapUserRolesToPermission(permission);
            }
            // Convert the list of Role enums to a list of GrantedAuthorities
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            userDetails = new User(username, user.getPassword(), authorities);
            authorizedUser = new AuthorizedUser(user, roles);
        }
        SessionUtil.getSession().setAttribute("user", authorizedUser);
        return userDetails;
    }

    private List<Role> mapUserRolesToPermission(PermissionDTO permission) {
        List<Role> roles = new ArrayList<>();

        if (permission.isAdmin()) {
            roles.add(Role.ADMIN);
        }

        if (permission.isTrainer()) {
            roles.add(Role.TRAINER);
        }

        return roles;
    }

}

