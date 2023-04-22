package com.tronina.avia.service.impl;

import com.tronina.avia.exception.UserAlreadyExistException;
import com.tronina.avia.model.entity.Customer;
import com.tronina.avia.repository.CustomersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomersRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Customer> loadByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void register(Customer dto) throws UserAlreadyExistException {
            Customer customer = Customer.builder()
                    .email(dto.getEmail())
                    .hashPassword(passwordEncoder.encode(dto.getHashPassword()))
                    .role(Customer.Role.USER)
                    .build();
            try {
                repository.save(customer);
            } catch (DataIntegrityViolationException e) {
//                throw new UserAlreadyExistException(dto.getEmail());
            }
        }
}
