package com.tronina.avia.controller;

import com.tronina.avia.entity.Airline;
import com.tronina.avia.entity.Airplane;
import com.tronina.avia.service.impl.AirlineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineController extends AbstractController<Airline, AirlineService> {

    public AirlineController(AirlineService service) {
        super(service);
    }

//    @Operation(summary = "Получить самолеты авикомпании")
    @GetMapping("/{id}/airplanes")
    public ResponseEntity<List<Airplane>> getPlanesOfAirline(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.findPlanesOfAirline(id));
    }

//    @Operation(summary = "Добавить самолет в авикомпанию")
    @PostMapping("/{id}/airplanes")
    public ResponseEntity<Airplane> addAirplane(@PathVariable(name = "id") Long id, @RequestBody Airplane element) {
        return new ResponseEntity(service.addPlaneToAirline(id, element), HttpStatus.CREATED);
    }
}
