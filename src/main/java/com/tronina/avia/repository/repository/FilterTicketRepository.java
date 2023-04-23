package com.tronina.avia.repository.repository;

import com.tronina.avia.model.dto.TicketFilter;
import com.tronina.avia.model.entity.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;
//@Repository
public interface FilterTicketRepository {//} extends QuerydslPredicateExecutor<QTick et.ticket> {

    List<Ticket> findAllByFilter(TicketFilter filter);
}
