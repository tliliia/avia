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

    public FlightDto addFligth(Long id, FlightDto flightDto) {
        Optional<Airplane> optionalPlane = findById(id);
        if (optionalPlane.isPresent()) {
            return flightService.setPlaneToFlight(optionalPlane.get(), flightDto);
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    //crud
    private Optional<Airplane> findById(Long id) {
        return repository.findById(id);
    }

    public AirplaneDto getById(Long id) {
        Optional<Airplane> optionalE = repository.findById(id);
        if (optionalE.isPresent()) {
            return mapper.toDto(optionalE.get());
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    public List<AirplaneDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Transactional
    public AirplaneDto create(AirplaneDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Transactional
    public AirplaneDto update(Long id, AirplaneDto dto) {
        Optional<Airplane> originalEntity = findById(id);
        if (originalEntity.isPresent()) {
            Airplane updated = (Airplane) originalEntity.get().updateFields(mapper.toEntity(dto));
            return mapper.toDto(repository.save(updated));
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    @Transactional
    public void delete(AirplaneDto dto) {
        Airplane entity = mapper.toEntity(dto);
        repository.delete(entity);
        logging.logDelete(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Airplane> optionalE = findById(id);
        if (optionalE.isPresent()) {
            repository.delete(optionalE.get());
        } else {
            throw new NotFoundEntityException(id);
        }
    }

}
