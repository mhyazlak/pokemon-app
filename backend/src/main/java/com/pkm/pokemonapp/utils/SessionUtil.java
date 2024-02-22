package com.pkm.pokemonapp.utils;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class SessionUtil {
    public static HttpSession getSession() {
        final RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        final ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        return attributes.getRequest().getSession();
    }
}