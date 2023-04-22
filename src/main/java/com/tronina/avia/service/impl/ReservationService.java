package com.tronina.avia.service.impl;

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

    private boolean isFree(Long id) {
        Optional<TicketReserve> optionalE = repository.findById(id);
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
        boolean isFree = isFree(ticket.getId());
        if (!isFree || ticket.getStatus() != Status.CREATED) {
            throw new RuntimeException("Билет не доступен для  бронирования");
        }
        repository.save(TicketReserve.builder()
                .ticket(ticket)
                .customer(customer)
                .expiredAt(LocalDateTime.now().plusMinutes(Long.parseLong(ticketReserveMinutes)))
                .build());
    }
}
