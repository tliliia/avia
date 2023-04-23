package com.tronina.avia.controller;

import com.tronina.avia.model.dto.AirlineDto;
import com.tronina.avia.model.dto.AirplaneDto;
import com.tronina.avia.model.dto.FlightDto;
import com.tronina.avia.service.impl.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/airlines")
public class AirlineController {

    private final AirlineService service;

    //    @Operation(summary = "Получить самолеты авикомпании")
    @GetMapping("/{id}/airplanes")
    public ResponseEntity<List<AirplaneDto>> getPlanesOfAirline(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.findPlanesOfAirline(id));
    }

    //    @Operation(summary = "количество самолётов у каждой авиакомпании")
    @GetMapping("/{id}/airplanes_count")
    public ResponseEntity<Long> getPlanesAmountOfAirline(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.findPlanesAmountOfAirline(id));
    }

    //    @Operation(summary = "Добавить самолет в авикомпанию")
    @PostMapping("/{id}/airplane")
    public ResponseEntity<AirplaneDto> addAirplane(@PathVariable(name = "id") Long id, @RequestBody AirplaneDto element) {
        return new ResponseEntity(service.addPlaneToAirline(id, element), HttpStatus.CREATED);
    }

    @PostMapping("/flight/{planeid}")
    public ResponseEntity<AirplaneDto> addFlight(@PathVariable(name = "planeid") Long id, @RequestBody FlightDto element) {
        return new ResponseEntity(service.addFligth(id, element), HttpStatus.CREATED);
    }

    //crud
    @GetMapping("/{id}")
    public ResponseEntity<AirlineDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<AirlineDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirlineDto> update(@RequestBody AirlineDto element, @PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(service.update(id, element));
    }

    @PostMapping
    public ResponseEntity<AirlineDto> create(@RequestBody AirlineDto element) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(element));
    }

    @DeleteMapping("/{elementId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long elementId) {
        service.deleteById(elementId);
        return ResponseEntity.accepted().build();
    }


}
