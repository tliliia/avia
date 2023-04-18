package com.tronina.avia.controller;

import com.tronina.avia.exception.UserAlreadyExistException;
import com.tronina.avia.model.dto.AuthCustomer;
import com.tronina.avia.model.dto.CustomerDto;
import com.tronina.avia.model.entity.Customer;
import com.tronina.avia.repository.CustomersRepository;
import com.tronina.avia.service.impl.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/jwttoken")
public class AuthController {
    private final CustomerService service;

    @Operation(summary = "Аутентификация пользователя")
    @PostMapping
    public ResponseEntity<AuthCustomer> auth(@RequestBody CustomerDto dto) throws UserAlreadyExistException {
        AuthCustomer token = service.register(dto);
        return ResponseEntity.ok(token);
    }

}
