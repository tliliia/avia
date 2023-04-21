package com.tronina.avia.repository;

import com.tronina.avia.model.entity.Ticket;
import com.tronina.avia.model.entity.TicketReserve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends BaseRepository<TicketReserve> {

}
