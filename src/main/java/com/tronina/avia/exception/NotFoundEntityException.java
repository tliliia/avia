package com.tronina.avia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ClientCode.NOT_FOUND)
public class NotFoundEntityException extends RuntimeException {
    public NotFoundEntityException(Long id) {
        super(String.format("Not found entity with id=%d", id));
    }
}
