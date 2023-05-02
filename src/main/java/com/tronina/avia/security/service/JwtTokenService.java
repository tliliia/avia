package com.tronina.avia.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class JwtTokenService {
    private static final long TOKEN_EXPIRES_TIME = 500 * 60 * 1000; // in MINUTES
    private static final String BEARER = "Bearer ";
    public static final String ROLE = "role";
    public static final String TOKEN_KEY = "accessToken";

    @Value("${jwt.secret}")
    private String secret;
    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
    }

    public boolean hasAuthToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return header != null && header.startsWith(BEARER);
    }

    private String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorizationHeader.substring(BEARER.length());
    }

    public Map<String, String> generateToken(String subject, String authority, String issuer) {
        String accessToken = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRES_TIME))
                .withClaim(ROLE, authority)
                .withIssuer(issuer)
                .sign(algorithm);

        return Collections.singletonMap(TOKEN_KEY, accessToken);
    }

    public UsernamePasswordAuthenticationToken buildAuthentication(HttpServletRequest request) throws JWTVerificationException {
        String token = getToken(request);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);//expiredate error
        String email = decodedJWT.getSubject();
        String role = decodedJWT.getClaim(ROLE).asString();
        Date expireDate = decodedJWT.getExpiresAt();

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null,
                            Collections.singleton(new SimpleGrantedAuthority(role)));
            return authenticationToken;
    }
}
