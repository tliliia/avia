package com.tronina.avia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ClientCode.ALREADY_EXIST)
public class UserAlreadyExistException extends Throwable {
    public UserAlreadyExistException(String email) {
            super(String.format("User with email=%d already exist", email));
    }
}
