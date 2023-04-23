package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.model.dto.AirlineDto;
import com.tronina.avia.model.dto.AirplaneDto;
import com.tronina.avia.model.dto.FlightDto;
import com.tronina.avia.model.entity.Airline;
import com.tronina.avia.model.mapper.AirlineMapper;
import com.tronina.avia.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AirlineService {
    private final AirlineRepository repository;
    private final LoggingService logging;
    private final AirplaneService planeService;

    private final AirlineMapper mapper = AirlineMapper.INSTANCE;

    public List<AirplaneDto> findPlanesOfAirline(Long id) {
        return planeService.findAllByAirlineId(id);
    }

    public Long findPlanesAmountOfAirline(Long id) {
        return planeService.countAirplanesByAirlineId(id);
    }

    public AirplaneDto addPlaneToAirline(Long id, AirplaneDto element) {
        Optional<Airline> optionalAirline = findById(id);
        if (optionalAirline.isPresent()) {
            return planeService.addPlaneToAirline(element, optionalAirline.get());
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    public FlightDto addFligth(Long id, FlightDto element) {
        return planeService.addFligth(id, element);
    }

    //crud
    private Optional<Airline> findById(Long id) {
        return repository.findById(id);
    }

    public AirlineDto getById(Long id) {
        Optional<Airline> optionalE = repository.findById(id);
        if (optionalE.isPresent()) {
            return mapper.toDto(optionalE.get());
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    public List<AirlineDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Transactional
    public AirlineDto create(AirlineDto entity) {
        return mapper.toDto(repository.save(mapper.toEntity(entity)));
    }

    @Transactional
    public AirlineDto update(Long id, AirlineDto dto) {
        Optional<Airline> originalEntity = findById(id);
        if (originalEntity.isPresent()) {
            Airline updated = (Airline) originalEntity.get().updateFields(mapper.toEntity(dto));
            return mapper.toDto(repository.save(updated));
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    @Transactional
    protected void delete(Airline entity) {
        repository.delete(entity);
        logging.logDelete(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Airline> optionalE = findById(id);
        if (optionalE.isPresent()) {
            delete(optionalE.get());
        } else {
            throw new NotFoundEntityException(id);
        }
    }
}
