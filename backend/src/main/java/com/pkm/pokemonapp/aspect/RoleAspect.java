// This is being used whenever this AccessRole annotation is being used on controller methods
// for basic Rolebased Access Control
package com.pkm.pokemonapp.aspect;

import com.pkm.pokemonapp.annotation.AccessRole;
import com.pkm.pokemonapp.enums.Role;
import com.pkm.pokemonapp.model.AuthorizedUser;
import com.pkm.pokemonapp.utils.SessionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Order(1)
@Component
public class RoleAspect {

    @Around("execution(* com.pkm.pokemonapp.controller.*.*(..)) and @annotation(accessRole)")
    public Object checkPermission(final ProceedingJoinPoint joinPoint, final AccessRole accessRole) throws Throwable {
        final AuthorizedUser user = (AuthorizedUser) SessionUtil.getSession().getAttribute("user");
        final List<Role> userRoles = user.getRoles(); // Assuming getRoles returns List<Role>
        final List<Role> requiredRoles = Arrays.asList(accessRole.roles());

        // Check if the user has any of the roles required by the method
        boolean hasRequiredRole = userRoles.stream().anyMatch(requiredRoles::contains);

        if (hasRequiredRole) {
            return joinPoint.proceed(); // User has at least one of the required roles
        } else {
            throw new SecurityException(user.getUser() + " does not have the required permission.");
        }
    }
}