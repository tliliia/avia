package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.model.dto.AirplaneDto;
import com.tronina.avia.model.dto.FlightDto;
import com.tronina.avia.model.entity.Airline;
import com.tronina.avia.model.entity.Airplane;
import com.tronina.avia.model.mapper.PlaneMapper;
import com.tronina.avia.repository.AirplaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AirplaneService {
    private final AirplaneRepository repository;
    private final LoggingService logging;
    private final FlightService flightService;

    private final PlaneMapper mapper = PlaneMapper.INSTANCE;


    public List<AirplaneDto> findAllByAirlineId(Long id) {
        return mapper.toDtoList(repository.findAllByAirlineId(id));
    }

    public Long countAirplanesByAirlineId(Long id) {
        return repository.countAirplanesByAirlineId(id);
    }

    public AirplaneDto addPlaneToAirline(AirplaneDto planeDto, Airline airline) {
        Airplane entity = mapper.toEntity(planeDto);
        entity.setAirline(airline);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public FlightDto buildFligthWithTickets(Long id, FlightDto flightDto, BigDecimal price) {
        Airplane plane = findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        return flightService.buildFligthWithTickets(plane, flightDto, price);
    }

    //crud
    private Optional<Airplane> findById(Long id) {
        return repository.findById(id);
    }

    public AirplaneDto getById(Long id) {
        Airplane entity = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        return mapper.toDto(entity);
    }

    public List<AirplaneDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Transactional
    public AirplaneDto update(Long id, AirplaneDto dto) {
        Airplane entity = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        Airplane updated = (Airplane) entity.updateFields(mapper.toEntity(dto));
        return mapper.toDto(repository.save(updated));
    }


    @Transactional
    public void deleteById(Long id) {
        Airplane entity = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        repository.delete(entity);
    }

}
