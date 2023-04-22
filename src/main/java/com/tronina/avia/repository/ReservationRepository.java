package com.tronina.avia.repository;

import com.tronina.avia.model.entity.TicketReserve;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReservationRepository extends BaseRepository<TicketReserve> {
    @Transactional
    @Modifying
    @Query(value = "delete FROM reservation where expired_time < now()",
            nativeQuery = true)
    Integer deleteExpiredReservatios();
}
