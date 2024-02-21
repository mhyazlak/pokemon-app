package com.pkm.pokemonapp.model;

import lombok.Data;

@Data
public class AjaxResponse {

    private boolean	success;
    private String	message;
    private Object	root;
    private Long	total;

    public AjaxResponse(boolean success) {
        this.success = success;
    }

    public AjaxResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AjaxResponse(boolean success, Object root, long total) {
        this.success = success;
        this.root = root;
        this.total = total;
    }

    public AjaxResponse(boolean success, String message, Object root, long total) {
        this.success = success;
        this.message = message;
        this.root = root;
        this.total = total;
    }
}