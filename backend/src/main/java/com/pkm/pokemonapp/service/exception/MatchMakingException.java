package com.pkm.pokemonapp.service.exception;

public class MatchMakingException extends RuntimeException {

    private static final long serialVersionUID = -366162040299648822L;

    public MatchMakingException() {
    }

    public MatchMakingException(String message) {
        super(message);
    }

    public MatchMakingException(Throwable cause) {
        super(cause);
    }

    public MatchMakingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchMakingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}