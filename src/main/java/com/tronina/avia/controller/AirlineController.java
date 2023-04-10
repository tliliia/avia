package com.tronina.avia.controller;

import com.tronina.avia.entity.Airline;
import com.tronina.avia.service.impl.AirlineService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airlines")
public class AirlineController extends AbstractController<Airline, AirlineService> {

    public AirlineController(AirlineService service) {
        super(service);
    }

}