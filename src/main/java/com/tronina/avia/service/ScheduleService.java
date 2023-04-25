package com.tronina.avia.service;

import com.tronina.avia.service.impl.ReservationCleaningService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleService {
    private final ReservationCleaningService service;

    //(секунда, минута, час, день месяца, месяц, день недели), * - любое
    //каждые 5 минут
    @Scheduled(cron = "0 */5 * * * *")
    public void cleanAnonymousCustomers() {
        service.cleanExpiredReservations();
    }
}
