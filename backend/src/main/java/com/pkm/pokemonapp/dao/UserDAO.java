package com.pkm.pokemonapp.dao;

import com.pkm.pokemonapp.dto.AuthorityDTO;
import com.pkm.pokemonapp.dto.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {


    private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM `User` where username = ?1";
    private static final String CREATE_USER = "INSERT INTO `User` (username, password) VALUES (?1, ?2)";
    private static final String SELECT_USER_ROLES = "SELECT * FROM `Authority` where user_id = ?1";

    @PersistenceContext
    EntityManager entityManager;

    public UserDTO readByUserName(String username) {
        Query query = entityManager.createNativeQuery(SELECT_USER_BY_USERNAME, UserDTO.class);
        query.setParameter(1, username);
        return (UserDTO) query.getSingleResult();
    }

    public void createNewUser(String username, String password) {
        Query query = entityManager.createNativeQuery(CREATE_USER);
        query.setParameter(1, username);
        query.setParameter(2, password);
        query.executeUpdate();
    }

    public AuthorityDTO getAuthorityByUserId(long userId) {
        Query query = entityManager.createNativeQuery(SELECT_USER_ROLES, AuthorityDTO.class);
        query.setParameter(1, userId);
        AuthorityDTO authorityDTO = (AuthorityDTO) query.getSingleResult();
        return authorityDTO;
    }
}
