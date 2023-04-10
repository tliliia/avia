package com.tronina.avia.service.impl;

import com.tronina.avia.entity.Airline;
import com.tronina.avia.repository.AirlineRepository;
import com.tronina.avia.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class AirlineService extends AbstractService<Airline, AirlineRepository> {

    public AirlineService(AirlineRepository repository) {
        super(repository);
    }
}