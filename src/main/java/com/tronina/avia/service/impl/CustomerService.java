package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.exception.UserAlreadyExistException;
import com.tronina.avia.model.dto.AuthCustomer;
import com.tronina.avia.model.dto.CustomerDto;
import com.tronina.avia.model.entity.Customer;
import com.tronina.avia.repository.CustomersRepository;
import com.tronina.avia.security.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomersRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService securityService;

    public Optional<Customer> loadByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Transactional
    public AuthCustomer register(CustomerDto dto) throws UserAlreadyExistException {
        Customer customer = Customer.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .build();
        try {
            repository.save(customer);
        } catch (DataIntegrityViolationException e) {
                throw new UserAlreadyExistException(dto.getEmail());
        }
        Map<String, String> jwtToken = securityService.generateToken(customer.getEmail(), customer.getRole().toString(), null);
        return new AuthCustomer(jwtToken.get(JwtTokenService.TOKEN_KEY));
    }

    public AuthCustomer authenticate(Customer dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );
        Customer customer = loadByEmail(dto.getEmail()).orElseThrow(() -> new NotFoundEntityException(0l));
        Map<String, String> jwtToken = securityService.generateToken(customer.getEmail(), customer.getRole().toString(), null);
        return new AuthCustomer(jwtToken.get(JwtTokenService.TOKEN_KEY));
    }
}
