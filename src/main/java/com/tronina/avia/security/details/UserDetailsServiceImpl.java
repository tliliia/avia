package com.tronina.avia.security.details;

import com.tronina.avia.model.entity.Customer;
import com.tronina.avia.repository.CustomersRepository;
import com.tronina.avia.security.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomersRepository repository;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        final Customer user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
        return new UserDetailsImpl(user.getId(), email, user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
    }

}
