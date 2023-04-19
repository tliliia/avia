package com.tronina.avia.exception;

public class UserAlreadyExistException extends Throwable {
    public UserAlreadyExistException(String email) {
            super(String.format("User with email=%d already exist", email));
    }
}
