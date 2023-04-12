package com.tronina.avia.service.impl;

import com.tronina.avia.entity.Ticket;
import com.tronina.avia.repository.TicketRepository;
import com.tronina.avia.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class TicketService extends AbstractService<Ticket, TicketRepository> {
    private static final String TICKET_COMISSION_SIZE = "ticket.comission";

    @Autowired
    private Environment environment;

    public TicketService(TicketRepository repository) {
        super(repository);
    }

    @Transactional
    public Ticket createWithComission(Ticket entity) {
        String comissionStringVal = String.valueOf(environment.getProperty(TICKET_COMISSION_SIZE));
        Double multiplyer = 1L + Double.parseDouble(comissionStringVal);
        entity.setPrice(entity.getPrice().multiply(BigDecimal.valueOf(multiplyer)));
        entity.setCommission(true);
        return repository.save(entity);
    }

    public Long countTicketsFromDeparture(String name) {
        return repository.countTicketsFromDeparture(name);
    }

    public Long countTicketsFromAirline(String name) {
        return repository.countTicketsFromAirline(name);
    }

    public Long countAvgOfComission() {
        return repository.countAvgOfComission();
    }
}
