/*
This class is putting in arguments into the controller methods to make session attributes easily accessed
*/
package com.pkm.pokemonapp.config;

import com.pkm.pokemonapp.dto.UserDTO;
import com.pkm.pokemonapp.model.AuthorizedUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthorizedUserArgumentResolver implements HandlerMethodArgumentResolver {

    // Extract the UserDTO from AuthorizedUser and put into the controller methods
    @Override
    public Object resolveArgument(final MethodParameter methodParameter, final ModelAndViewContainer modelViewContainer,
                                  final NativeWebRequest nativeWebRequest, final WebDataBinderFactory webDataBinderFactory) throws Exception {
        final HttpSession session = ((HttpServletRequest) nativeWebRequest.getNativeRequest()).getSession();
        final AuthorizedUser authorizedUser = (AuthorizedUser) session.getAttribute("user");
        final UserDTO user = authorizedUser.getUser();
        return user;
    }

    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(UserDTO.class);
    }

}