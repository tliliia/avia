package com.tronina.avia.controller;

import com.tronina.avia.model.dto.AirplaneDto;
import com.tronina.avia.model.dto.FlightDto;
import com.tronina.avia.service.impl.AirplaneService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService service;

    @Operation(summary = "Добавить рейс на самолет")
    @PostMapping("/{id}/flight/")
    public ResponseEntity<FlightDto> addFlight(@PathVariable(name = "id") Long id,
                                               @RequestParam(name = "price") Long price,
                                               @RequestBody FlightDto dto) {
        return new ResponseEntity(service.buildFligthWithTickets(id, dto, BigDecimal.valueOf(price)), HttpStatus.CREATED);
    }


    //crud
    @GetMapping("/{id}")
    public ResponseEntity<AirplaneDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<AirplaneDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirplaneDto> update(@RequestBody AirplaneDto element, @PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(service.update(id, element));
    }

    @DeleteMapping("/{elementId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long elementId) {
        service.deleteById(elementId);
        return ResponseEntity.accepted().build();
    }

}
