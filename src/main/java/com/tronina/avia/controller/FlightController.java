package com.tronina.avia.controller;

import com.tronina.avia.model.dto.AirplaneDto;
import com.tronina.avia.model.dto.FlightDto;
import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.service.impl.FlightService;
import com.tronina.avia.service.impl.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService service;
    private final TicketService ticketService;

    @Operation(summary = "Получить билеты для рейса")
    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketDto>> getTicketsOfFligth(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(ticketService.getTicketsOfFligth(id));
    }

    //crud
    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<FlightDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDto> update(@RequestBody FlightDto element, @PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(service.update(id, element));
    }

    @DeleteMapping("/{elementId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long elementId) {
        service.deleteById(elementId);
        return ResponseEntity.accepted().build();
    }
}
