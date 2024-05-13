package com.pkm.pokemonapp.dao;

import com.pkm.pokemonapp.dto.AuthorityDTO;
import com.pkm.pokemonapp.dto.PermissionDTO;
import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.model.UserRoles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {


    private static final String SELECT_FOR_LOGIN = "SELECT * FROM `User` where username = ?1";
    private static final String CALL_CREATE_USER = "CALL CREATE_USER(?1, ?2, ?3, ?4)";
    private static final String SELECT_USER_ROLES = "SELECT * FROM `Permission` where user_id = ?1";

    private static final String USERNAME_EXISTS ="SELECT COUNT(*) FROM `User` where username = ?1";
    private static final String EMAIL_EXISTS ="SELECT COUNT(*) FROM `User` where email = ?1";
    private static final String ALIASNAME_EXISTS ="SELECT COUNT(*) FROM `User` where alias_name = ?1";

    @PersistenceContext
    EntityManager entityManager;

    // This is needed for CustomUserDetailsService
    public UserDTO readByUserName(String username) {
        Query query = entityManager.createNativeQuery(SELECT_FOR_LOGIN, UserDTO.class);
        query.setParameter(1, username);
        return (UserDTO) query.getSingleResult();
    }

    // This function is used to registrate a new user
    public long createNewUser(String username, String password, String email, String aliasName) {
        Query query = entityManager.createNativeQuery(CALL_CREATE_USER);
        query.setParameter(1, username);
        query.setParameter(2, password);
        query.setParameter(3, email);
        query.setParameter(4, aliasName);
        return (long) query.getSingleResult();
    }



    public boolean userNameExists(String username) {
        Query query = entityManager.createNativeQuery(USERNAME_EXISTS);
        query.setParameter(1, username);
        return 0 < (long) query.getSingleResult();
    }

    public boolean emailExists(String email) {
        Query query = entityManager.createNativeQuery(EMAIL_EXISTS);
        query.setParameter(1, email);
        return 0 < (long) query.getSingleResult();
    }

    public boolean aliasNameExists(String aliasName) {
        Query query = entityManager.createNativeQuery(ALIASNAME_EXISTS);
        query.setParameter(1, aliasName);
        return 0 < (long) query.getSingleResult();
    }

    public PermissionDTO getUserPermission(long id) {
        Query query = entityManager.createNativeQuery(SELECT_USER_ROLES, PermissionDTO.class);
        query.setParameter(1, id);
        PermissionDTO permission = (PermissionDTO) query.getSingleResult();
        return permission;
    }
}
