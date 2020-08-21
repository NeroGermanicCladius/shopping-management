package com.example.configuration.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Responsible for injecting Logged in user's principal into endpoint methods
 */
public class PrincipalMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(UserPrincipal.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }

        final Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }

        final Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserPrincipal)) {
            return null;
        }

        return principal;
    }
}