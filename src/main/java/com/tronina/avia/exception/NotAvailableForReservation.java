package com.tronina.avia.exception;

public class NotAvailableForReservation extends RuntimeException {
    public NotAvailableForReservation(Long id) {
        super(String.format("Not available for reservation ticket with id=%d", id));
    }
}
