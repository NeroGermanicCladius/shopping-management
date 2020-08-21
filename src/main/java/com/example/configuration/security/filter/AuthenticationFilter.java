package com.example.configuration.security.filter;

import com.example.configuration.ApplicationProperties;
import com.example.configuration.security.JwtService;
import com.example.configuration.security.UserPrincipal;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final AuthenticationManager authManger;
    private final JwtService jwtService;
    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(
            final AuthenticationManager authManger,
            final JwtService jwtService,
            final ApplicationProperties applicationProperties
    ) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = new ObjectMapper();
        this.authManger = authManger;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        LOGGER.debug("Trying to authenticate an User...");
        try {
            final Credentials credentials = readCredentials(request);
            return authManger.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(),
                    credentials.getPassword()
            ));
        } catch (final IOException e) {
            LOGGER.warn("Authentication process failed.", e);
        }

        return null; // null by contract
    }

    private Credentials readCredentials(final HttpServletRequest request) throws IOException {
        return objectMapper.readValue(
                request.getReader().lines().collect(Collectors.joining(System.lineSeparator())),
                Credentials.class
        );
    }

    @Override
    public void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult
    ) {
        final UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
        final String token = jwtService.generateToken(principal);
        response.addHeader(applicationProperties.getSecurity().getAuthTokenKey(), token);
        LOGGER.debug("Successfully authenticated the User:'{}'.", principal.getUsername());
    }

    @Override
    public void unsuccessfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException failed
    ) throws IOException {
        LOGGER.warn("User authentication has failed. Returning unauthorized...");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Provided credentials are wrong.");
    }

    private static class Credentials {
        private final String email;
        private final String password;

        @JsonCreator
        private Credentials(
                @JsonProperty("email") final String email,
                @JsonProperty("password") final String password
        ) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}