package com.pkm.pokemonapp.service.exception;

public class InvalidPlayerActionException extends RuntimeException {

    private static final long serialVersionUID = -366162040299648822L;

    public InvalidPlayerActionException() {
    }

    public InvalidPlayerActionException(String message) {
        super(message);
    }

    public InvalidPlayerActionException(Throwable cause) {
        super(cause);
    }

    public InvalidPlayerActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPlayerActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}