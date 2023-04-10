package com.tronina.avia.controller;

import com.tronina.avia.entity.Airplane;
import com.tronina.avia.entity.Flight;
import com.tronina.avia.service.impl.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController extends AbstractController<Flight, FlightService> {

    public FlightController(FlightService service) {
        super(service);
    }

}
