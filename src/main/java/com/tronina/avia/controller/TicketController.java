package com.tronina.avia.controller;

import com.tronina.avia.entity.Ticket;
import com.tronina.avia.service.impl.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController extends AbstractController<Ticket, TicketService> {

    public TicketController(TicketService service) {
        super(service);
    }

//    @Operation(summary = "Создать билет с комиссией сервиса")
    @PostMapping("/comission")
    public ResponseEntity<Ticket> createWithComission(@RequestBody Ticket element) {
        return new ResponseEntity(service.createWithComission(element), HttpStatus.CREATED);
    }
}
