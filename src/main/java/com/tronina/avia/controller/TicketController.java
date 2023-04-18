package com.tronina.avia.controller;

import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.model.entity.Customer;
import com.tronina.avia.service.impl.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService service;

    @Operation(summary = "Получить доступные билеты")
    @GetMapping("/free")
    public ResponseEntity<List<TicketDto>>getFreeTickets() {
        return ResponseEntity.ok(service.findAllAvailableTickets(LocalDateTime.now()));
    }

    @Operation(summary = "Получить билеты для представителя. Проданные билеты не должны отображаться в списке у представителя, если в запросе не был передан соответствующий флаг.")
    @PreAuthorize("hasAuthority('AGENT')")
    @GetMapping("/stat/free")
    public ResponseEntity<List<TicketDto>>getAgentTickets(@RequestParam String airline, boolean showSold, @AuthenticationPrincipal Customer customer) {
        return ResponseEntity.ok(service.finadAllForAgent(airline, showSold));
    }

    @Operation(summary = "Получить статистику продаж для авиакомпании Представителя")
    @PreAuthorize("hasAuthority('AGENT')")
    @GetMapping("/stat/departure")
    public ResponseEntity<Long>countTicketsFromDeparture(@RequestParam String departure, @AuthenticationPrincipal Customer customer) {
        return ResponseEntity.ok(service.countTicketsFromDeparture(departure));
    }

    @Operation(summary = "Получить статистику продаж для авиакомпании Представителя")
    @PreAuthorize("hasAuthority('AGENT')")
    @GetMapping("/stat/airline")
    public ResponseEntity<Long>countTicketsFromAirline(@RequestParam String airline, @AuthenticationPrincipal Customer customer) {
        return ResponseEntity.ok(service.countTicketsFromAirline(airline));
    }

    @Operation(summary = "Получить статистику продаж для авиакомпании Представителя")
    @PreAuthorize("hasAuthority('AGENT')")
    @GetMapping("/stat/comission")
    public ResponseEntity<Long>countTicketsComissionAvg(@RequestParam String airline, @AuthenticationPrincipal Customer customer) {
        return ResponseEntity.ok(service.countAvgOfComission(airline));
    }

    @Operation(summary = "Покупатель - может забронировать билет, но не может купить до подтверждения кассиром")
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("reserve/{id}")
    public ResponseEntity<?>doReservation(@PathVariable Long id, @AuthenticationPrincipal Customer customer) {
        service.doReservation(id, customer);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Кассир может подтвердить а также снимать статус “забронирован”")
    @PreAuthorize("hasAuthority('SALESMAN')")
    @PostMapping("status/{id}")
    public ResponseEntity<?>changeReservationStatus(@PathVariable Long id, @RequestParam boolean confirmed, @AuthenticationPrincipal Customer customer) {
        service.confirmReservation(id, confirmed);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Кассир может продавать билеты")
    @PreAuthorize("hasAuthority('SALESMAN')")
    @PostMapping("/order/{id}")
    public ResponseEntity<?>makeOrder(@PathVariable Long id, @RequestParam String promo, @AuthenticationPrincipal Customer customer) {
        service.makeOrder(id, customer, null);
        return ResponseEntity.ok().build();
    }
}
