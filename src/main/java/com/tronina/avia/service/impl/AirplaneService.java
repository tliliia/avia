package com.tronina.avia.service.impl;

import com.tronina.avia.entity.Airplane;
import com.tronina.avia.repository.AirplaneRepository;
import com.tronina.avia.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class AirplaneService extends AbstractService<Airplane, AirplaneRepository> {

    public AirplaneService(AirplaneRepository repository) {
        super(repository);
    }
}
