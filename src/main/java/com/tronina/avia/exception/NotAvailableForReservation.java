package com.tronina.avia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ClientCode.NOT_AVAILABLE_OPERATION)
public class NotAvailableForReservation extends RuntimeException {
    public NotAvailableForReservation(Long id) {
        super(String.format("Not available for reservation ticket with id=%d", id));
    }
}
