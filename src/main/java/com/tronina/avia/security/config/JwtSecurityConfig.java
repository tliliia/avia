package com.tronina.avia.security.config;

import com.tronina.avia.model.entity.Customer;
import com.tronina.avia.security.filters.JwtAuthorizationFilter;
import com.tronina.avia.security.filters.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
@EnableWebSecurity(debug = true)
public class JwtSecurityConfig {
    public static final String AUTHENTICATION_URL = "/jwttoken";
    private final UserDetailsService jwtUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        //заинжектится к public TokenAuthenticationFilter(AuthenticationConfiguration authenticationConfiguration)
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
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.authorizeRequests()
                .antMatchers(AUTHENTICATION_URL).permitAll()
                .antMatchers("/tickets/free").permitAll()
                .antMatchers("/tickets/statistic/").hasAnyRole(Customer.Role.AGENT.toString())//Проданные билеты не должны отображаться в списке у представителя, если в запросе не был передан соответствующий флаг
                .antMatchers(HttpMethod.POST, "/tickets/confirm").hasAuthority(Customer.Role.SALESMAN.toString())
                .antMatchers(HttpMethod.POST, "/tickets/reserve").hasAuthority(Customer.Role.USER.toString())
                .antMatchers(HttpMethod.POST, "/tickets/purchase").hasAuthority(Customer.Role.USER.toString())
                .anyRequest().authenticated();
        return httpSecurity.build();
    }

}
