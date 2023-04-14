package com.tronina.avia.controller;

import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.service.impl.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/flights")
public class FlightController {

    private final TicketService ticketService;

    @Operation(summary = "Получить билеты для рейса")
    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketDto>> getTicketsOfFligth(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(ticketService.getTicketsOfFligth(id));
    }

}
