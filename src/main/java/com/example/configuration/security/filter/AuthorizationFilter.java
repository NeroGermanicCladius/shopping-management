package com.example.configuration.security.filter;

import com.example.configuration.ApplicationProperties;
import com.example.configuration.security.JwtService;
import com.example.configuration.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtService jwtService;
    private final ApplicationProperties applicationProperties;

    public AuthorizationFilter(
            final AuthenticationManager authenticationManager,
            final JwtService jwtService,
            final ApplicationProperties applicationProperties
    ) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.applicationProperties = applicationProperties;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain
    ) throws IOException, ServletException {
        final String authTokenKey = applicationProperties.getSecurity().getAuthTokenKey();

        final String token = request.getHeader(authTokenKey);
        final Authentication authentication = jwtService.convert(token);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (jwtService.isTokenExpiring(token)) {
                response.addHeader(authTokenKey, jwtService.generateToken((UserPrincipal) authentication.getPrincipal()));
            }
        }
        chain.doFilter(request, response);
    }

}