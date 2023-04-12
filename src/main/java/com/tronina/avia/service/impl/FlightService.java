package com.tronina.avia.service.impl;

import com.tronina.avia.entity.Flight;
import com.tronina.avia.repository.FlightRepository;
import com.tronina.avia.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class FlightService extends AbstractService<Flight, FlightRepository> {

    public FlightService(FlightRepository repository) {
        super(repository);
    }

}
