package com.pkm.pokemonapp.config;

import com.pkm.pokemonapp.model.AuthorizedUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthorizedUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(final MethodParameter methodParameter, final ModelAndViewContainer modelViewContainer,
                                  final NativeWebRequest nativeWebRequest, final WebDataBinderFactory webDataBinderFactory) throws Exception {
        final HttpSession session = ((HttpServletRequest) nativeWebRequest.getNativeRequest()).getSession();
        final AuthorizedUser user = (AuthorizedUser) session.getAttribute("user");
        return user;
    }

    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(AuthorizedUser.class);
    }

}