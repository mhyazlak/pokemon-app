package com.pkm.pokemonapp.service.exception;

import lombok.Getter;

import java.util.List;


public class RegistrationException extends Exception {

    private static final long serialVersionUID = -366162040299648822L;

    @Getter
    private List<String> errorList;

    public RegistrationException() {
    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(Throwable cause) {
        super(cause);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationException(String message, Throwable cause, List<String> errorList) {
        super(message, cause);
        this.errorList = errorList;
    }

    public RegistrationException(List<String> errorList) {
        this.errorList = errorList;
    }

}
