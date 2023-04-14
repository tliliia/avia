package com.tronina.avia.controller;

import com.tronina.avia.model.entity.Ticket;
import com.tronina.avia.service.impl.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    /*

    public TicketController(TicketService service) {
        super(service);
    }

//    @Operation(summary = "Создать билет с комиссией сервиса")
    @PostMapping("/comission")
    public ResponseEntity<Ticket> createWithComission(@RequestBody Ticket element) {
        return new ResponseEntity(service.createWithComission(element), HttpStatus.CREATED);
    }

//    @Operation(summary = "общее количество проданных билетов с точкой отправления departure")
    @GetMapping("/sold_by_departure/{name}")
    public ResponseEntity<Long> countAllSoldFromDeparture(@RequestParam String name) {
        return ResponseEntity.ok(service.countTicketsFromDeparture(name));
    }

//    @Operation(summary = "количество проданных билетов по авиакомпании")
    @GetMapping("/sold_by_avialine/{name}")
    public ResponseEntity<Long> countAllSoldFromAvialine(@RequestParam String name) {
        return ResponseEntity.ok(service.countTicketsFromAirline(name));
    }

    //    @Operation(summary = "средняя сумма комиссии за билеты")
    @GetMapping("/avg_of_comission")
    public ResponseEntity<Long> countAvgOfComission() {
        return ResponseEntity.ok(service.countAvgOfComission());
    }

     */
}
