package com.tronina.avia.security.config;

import com.tronina.avia.security.filters.JwtAuthorizationFilter;
import com.tronina.avia.security.filters.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class JwtSecurityConfig {
    public static final String AUTHENTICATION_URL = "/jwttoken/**" ;
    private final UserDetailsService jwtUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        //заинжектится к public JwtAuthenticationFilter(ObjectMapper objectMapper,AuthenticationConfiguration authenticationConfiguration)
        builder.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   TokenAuthenticationFilter tokenAuthenticationFilter,
                                                   JwtAuthorizationFilter jwtAuthorizationFilter) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.cors().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilter(tokenAuthenticationFilter);
//        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.authorizeRequests()
                .antMatchers(AUTHENTICATION_URL).permitAll()
                .antMatchers("/tickets/**").authenticated();
        return httpSecurity.build();
//        .authorizeHttpRequests()
//                .requestMatchers("/", "/authenticate").permitAll()
//                .anyRequest().hasRole(USER).and()
//        httpSecurity.authorizeRequests()
//                .antMatchers("/api/login/**").permitAll()
//                .antMatchers(HttpMethod.POST, STUDENTS_API).hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.PUT, STUDENTS_API).hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.DELETE, STUDENTS_API).hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.GET, STUDENTS_API).authenticated();
    }

}
