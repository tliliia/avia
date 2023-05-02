package com.tronina.avia.controller;

import com.tronina.avia.model.dto.AirplaneDto;
import com.tronina.avia.model.entity.Airplane;
import com.tronina.avia.service.impl.AirlineService;
import com.tronina.avia.service.impl.AirplaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService service;

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

    //создается без привязки к авикомпании
    @PostMapping
    public ResponseEntity<AirplaneDto> create(@RequestBody AirplaneDto element) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(element));
    }

    @DeleteMapping("/{elementId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long elementId) {
        service.deleteById(elementId);
        return ResponseEntity.accepted().build();
    }

}
