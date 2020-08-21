package com.example.configuration.security;

import com.example.configuration.ApplicationProperties;
import com.example.configuration.security.filter.AuthenticationFilter;
import com.example.configuration.security.filter.AuthorizationFilter;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;
    private final UserService userService;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public SecurityConfiguration(
            final JwtService jwtService,
            final UserService userService,
            final ApplicationProperties applicationProperties
    ) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.applicationProperties = applicationProperties;
    }

    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        final String adminAuthority = "ADMIN";
        final String userAuthority = "USER";

        httpSecurity
                .cors().configurationSource(corsConfig())
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(POST, "/login").permitAll()
                .antMatchers(GET, "/swagger-ui.html").permitAll()
                .antMatchers(GET, "/webjars/**").permitAll()
                .antMatchers(GET, "/swagger-resources/**").permitAll()
                .antMatchers(GET, "/v2/api-docs").permitAll()
                .antMatchers(POST, "/api/v1/user/register").permitAll()
                .antMatchers(GET, "/api/v1/user/getAll").hasAuthority(adminAuthority)
                .antMatchers(GET, "/api/v1/user/get").hasAuthority(adminAuthority)
                .antMatchers(POST, "/api/v1/user/block").hasAuthority(adminAuthority)
                .antMatchers(POST, "/api/v1/user/unblock").hasAuthority(adminAuthority)
                .antMatchers(DELETE, "/api/v1/user/delete").hasAuthority(adminAuthority)
                .antMatchers("/api/v1/comment/**").hasAnyAuthority(adminAuthority, userAuthority)
                .antMatchers("/api/v1/product/**").hasAuthority(adminAuthority)
                .antMatchers(GET, "/api/v1/product/all").permitAll()
                .antMatchers(POST, "/api/v1/rating/addRating").hasAnyAuthority(adminAuthority, userAuthority)
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager(), jwtService, applicationProperties))
                .addFilter(new AuthorizationFilter(authenticationManager(), jwtService, applicationProperties));
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    private CorsConfigurationSource corsConfig() {
        final var source = new UrlBasedCorsConfigurationSource();
        final var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(CorsConfiguration.ALL));
        corsConfiguration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        corsConfiguration.setAllowCredentials(Boolean.TRUE);
        corsConfiguration.setExposedHeaders(List.of("Content-Disposition", "Location", "Authorization", "jwt-auth-token"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "jwt-auth-token", "Access-Control-Allow-Origin", "Content-Type", "Cache-Control", "X-Requested-With"));
        source.registerCorsConfiguration(applicationProperties.getSecurity().getCorsAllowAllPattern(), corsConfiguration);
        return source;
    }
}
