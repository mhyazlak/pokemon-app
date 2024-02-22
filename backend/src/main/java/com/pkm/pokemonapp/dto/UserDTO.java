package com.pkm.pokemonapp.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pkm.pokemonapp.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//public class UserDTO implements UserDetails {

@Data
@Entity
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Id
    @Column(name = "id")
    private long id;

    private String username;

    @JsonIgnore
    private String password;

    public UserDTO(long userId, String username, String password, Set<Role> authorities) {
        this.id = userId;
        this.username = username;
        //this.password = password;
    }

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }*/
}