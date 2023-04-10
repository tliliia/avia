package com.tronina.avia.service.impl;

import com.tronina.avia.entity.Ticket;
import com.tronina.avia.repository.TicketRepository;
import com.tronina.avia.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class TicketService extends AbstractService<Ticket, TicketRepository> {
    public TicketService(TicketRepository repository) {
        super(repository);
    }
}