package com.tronina.avia.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tronina.avia.security.config.JwtSecurityConfig;
import com.tronina.avia.security.details.UserDetailsImpl;
import com.tronina.avia.security.service.JwtTokenService;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String USERNAME_PARAMETER = "email";

    private final ObjectMapper objectMapper;
    private final JwtTokenService jwtTokenService;

    public TokenAuthenticationFilter(ObjectMapper objectMapper,
                                     JwtTokenService jwtTokenService,
                                     AuthenticationConfiguration authenticationConfiguration) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        this.setUsernameParameter(USERNAME_PARAMETER);
        this.setFilterProcessesUrl(JwtSecurityConfig.AUTHENTICATION_URL);
        this.objectMapper = objectMapper;
        this.jwtTokenService = jwtTokenService;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        response.setContentType("application/json");

        Map<String, String> tokenJson = jwtTokenService.generateToken(
                userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURL().toString());

        objectMapper.writeValue(response.getOutputStream(), tokenJson);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

