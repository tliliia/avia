package com.tronina.avia.repository.repository;

import com.tronina.avia.model.dto.TicketFilter;
import com.tronina.avia.model.entity.Ticket;

import java.util.List;

public interface FilterTicketRepository {

    List<Ticket> findAllByFilter(TicketFilter filter);
}
