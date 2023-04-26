package com.tronina.avia.service.impl;

import com.tronina.avia.model.entity.TicketReserve;
import com.tronina.avia.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationCleaningService {
    private final ReservationRepository repository;

    public Integer cleanExpiredReservations() {
        Integer deletedCount = repository.deleteExpiredReservatios();
        return deletedCount;
    }
}
