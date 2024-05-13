package com.pkm.pokemonapp.service.impl;

import com.pkm.pokemonapp.dao.UserDAO;
import com.pkm.pokemonapp.dto.LoginDTO;
import com.pkm.pokemonapp.dto.RegistrationDTO;
import com.pkm.pokemonapp.enums.Role;
import com.pkm.pokemonapp.service.IAuthenticationService;
import com.pkm.pokemonapp.service.exception.RegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public String loginUser(LoginDTO login) {
        //TODO implement a check if the account is activated or not by email

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
        );
        String token = tokenService.generateJwt(authentication);
        return token;
    }

    @Transactional
    public void registerUser(RegistrationDTO registrationDTO) throws RegistrationException {
        List<String> errorList = new ArrayList<>();

        // Check if username already exists
        if (userDAO.userNameExists(registrationDTO.getUsername())) {
            errorList.add("username");
        }
        // Check if password is strong enough
        if (!checkPasswordStrength(registrationDTO.getPassword())) {
            errorList.add("password");
        }
        // Check if email-address already exists
        if (isValidEmail(registrationDTO.getEmail()) && userDAO.emailExists(registrationDTO.getEmail())) {
            errorList.add("email");
        }
        // Check if aliasName already exists
        if (userDAO.aliasNameExists(registrationDTO.getAliasName())) {
            errorList.add("aliasName");
        }

        // Check if any errors have occurred while checking, throw Exception if thats the case
        // TODO deliver reason for password
        if (!errorList.isEmpty()) {
            throw new RegistrationException(errorList);
        }

        String encodePassword = passwordEncoder.encode(registrationDTO.getPassword());
        Set<Role> authorities = new HashSet<>();
        userDAO.createNewUser(registrationDTO.getUsername(),
                encodePassword,
                registrationDTO.getEmail(),
                registrationDTO.getAliasName());
    }

    @Override
    public String authenticateToken(String token) {
        //TODO implement function that takes a user token and verifies its validity
        return null;
    }

    private boolean checkPasswordStrength(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        int length = password.length();

        if (length < 8) { // Check for minimum length
            return false;
        }

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
                if (Character.isUpperCase(c)) {
                    hasUpperCase = true;
                } else if (Character.isLowerCase(c)) {
                    hasLowerCase = true;
                }
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecialChar = true;
            }

            // If all conditions are met, no need to check further
            if (hasLetter && hasDigit && hasSpecialChar && hasUpperCase && hasLowerCase) {
                return true;
            }
        }
        // Return false if not all conditions are met
        return false;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty() || email.length() > 100) {
            return false;
        }

        String emailRegex = "^(?=.{1,256}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^\\-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return email.matches(emailRegex);
    }


}
