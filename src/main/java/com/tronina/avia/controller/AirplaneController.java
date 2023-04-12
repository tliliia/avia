package com.tronina.avia.controller;

import com.tronina.avia.entity.Airplane;
import com.tronina.avia.service.impl.AirplaneService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airplanes")
public class AirplaneController extends AbstractController<Airplane, AirplaneService> {

    public AirplaneController(AirplaneService service) {
        super(service);
    }

}
