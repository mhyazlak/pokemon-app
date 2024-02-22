package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.UserDAO;
import com.pkm.pokemonapp.dto.AuthorityDTO;
import com.pkm.pokemonapp.enums.Role;
import com.pkm.pokemonapp.model.AuthorizedUser;
import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        AuthorizedUser authorizedUser = null;
        if (!"unkown".equalsIgnoreCase(username)) {
            Role role = Role.USER;
            UserDTO user = userDAO.readByUserName(username);

            final AuthorityDTO authority = userDAO.getAuthorityByUserId(user.getId());
            if (authority != null) {
                role = Role.fromString(authority.getAuthority());
            }
            userDetails = new User(username, user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(role.getName())));
            authorizedUser = new AuthorizedUser(user, role);
        }
        SessionUtil.getSession().setAttribute("user", authorizedUser);
        return userDetails;
    }
}
