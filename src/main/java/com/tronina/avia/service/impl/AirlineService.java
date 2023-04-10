package com.tronina.avia.service.impl;

import com.tronina.avia.entity.Airline;
import com.tronina.avia.entity.Airplane;
import com.tronina.avia.repository.AirlineRepository;
import com.tronina.avia.repository.AirplaneRepository;
import com.tronina.avia.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService extends AbstractService<Airline, AirlineRepository> {

    public AirlineService(AirlineRepository repository) {
        super(repository);
    }

    @Autowired
    private AirplaneRepository airplaneRepository;

    public List<Airplane> findPlanesOfAirline(Long id) {
        return airplaneRepository.findAllByAirlineId(id);
    }

    public Airplane addPlaneToAirline(Long id, Airplane element) {
        Airline airline = findById(id);
        airline.getAirplanes().add(element);
        return airplaneRepository.save(element);
    }
}
