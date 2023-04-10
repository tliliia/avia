package com.tronina.avia.controller;

import com.tronina.avia.entity.Ticket;
import com.tronina.avia.service.impl.TicketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController extends AbstractController<Ticket, TicketService> {

    public TicketController(TicketService service) {
        super(service);
    }
}