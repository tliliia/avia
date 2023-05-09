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

    public boolean isFree(Ticket ticket) {
        Optional<TicketReserve> optionalE = repository.findById(ticket.getId());
        if (!optionalE.isPresent()) {
        //Если в таблице с резервом нет строки на данный билет, то он свободен
            return true;
        } else {
            final TicketReserve reservation = optionalE.get();
            boolean expired = reservation.getExpiredAt().isAfter(LocalDateTime.now());
            //По истечению времени бронирования бронь должна слетать и билет должен быть доступен к повторному бронированию
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
//                .customer(customer)
                .expiredAt(LocalDateTime.now().plusMinutes(Long.parseLong(ticketReserveMinutes)))
                .build());
    }
}
