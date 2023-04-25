package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotAvailableForReservation;
import com.tronina.avia.model.entity.Customer;
import com.tronina.avia.model.entity.Status;
import com.tronina.avia.model.entity.Ticket;
import com.tronina.avia.model.entity.TicketReserve;
import com.tronina.avia.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository repository;
    @Value("${ticket.reserve}")
    private String ticketReserveMinutes;

    private boolean isFree(Ticket ticket) {
        if (ticket.getStatus() != Status.CREATED) {
            return false;
        }
        Optional<TicketReserve> optionalE = repository.findById(ticket.getId());
        if (!optionalE.isPresent()) {
            return true;
        } else {
            final TicketReserve reservation = optionalE.get();
            boolean expired = reservation.getExpiredAt().isAfter(LocalDateTime.now());
            if (expired) {
                repository.delete(reservation);
            }
            return expired;
        }
    }

    public void doReservation(Ticket ticket, Customer customer) {
        if (!isFree(ticket)) {
            throw new NotAvailableForReservation(ticket.getId());
        }
        repository.save(TicketReserve.builder()
                .ticket(ticket)
                .customer(customer)
                .expiredAt(LocalDateTime.now().plusMinutes(Long.parseLong(ticketReserveMinutes)))
                .build());
    }
}
