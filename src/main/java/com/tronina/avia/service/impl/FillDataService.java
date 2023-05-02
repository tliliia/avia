package com.tronina.avia.service.impl;

import com.tronina.avia.exception.UserAlreadyExistException;
import com.tronina.avia.model.entity.Customer;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class FillDataService {
    private final CustomerService service;

    @PostConstruct
    public void init() throws UserAlreadyExistException {
        service.register(new Customer("tronina@mail.ru", "111", Customer.Role.USER));
    }
}
