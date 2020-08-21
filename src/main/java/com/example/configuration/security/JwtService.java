package com.example.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.example.configuration.ApplicationProperties;
import com.example.model.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String EMAIL_KEY = "email";
    private static final String USER_ID_KEY = "userId";
    private static final String AUTHORITIES_KEY = "authorities";

    private final UserService userService;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public JwtService(final UserService userService, final ApplicationProperties applicationProperties) {
        this.userService = userService;
        this.applicationProperties = applicationProperties;
    }

    public String generateToken(final UserPrincipal principal) {
        final User user = principal.getUser();
        final ApplicationProperties.SecurityProperties securityProperties = applicationProperties.getSecurity();
        return JWT.create()
                .withClaim(EMAIL_KEY, user.getEmail())
                .withClaim(USER_ID_KEY, user.getId())
                .withArrayClaim(AUTHORITIES_KEY, principalToArray(principal.getAuthorities()))
                .withExpiresAt(new Date(System.currentTimeMillis() + securityProperties.getExpirationMillis()))
                .sign(Algorithm.HMAC512(securityProperties.getJwtSecret()));
    }

    public Authentication convert(final String token) {
        if (token == null) {
            return null;
        }

        final Map<String, Claim> claimMap = verifyAndParseToken(token);
        final Long userId = claimMap.get(USER_ID_KEY).as(Long.class);
        final List<SimpleGrantedAuthority> authorities = claimMap.get(AUTHORITIES_KEY)
                .asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(new UserPrincipal(userService.get(userId)), null, authorities);
    }

    private Map<String, Claim> verifyAndParseToken(final String token) {
        return JWT.require(Algorithm.HMAC512(applicationProperties.getSecurity().getJwtSecret()))
                .build()
                .verify(token)
                .getClaims();
    }

    public boolean isTokenExpiring(final String token) {
        final Date expirationDate = JWT.decode(token).getExpiresAt();
        return System.currentTimeMillis() - expirationDate.getTime() < 5 * 60 * 1000;
    }

    private String[] principalToArray(final Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
    }
}
