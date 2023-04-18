package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.model.dto.AirlineDto;
import com.tronina.avia.model.dto.AirplaneDto;
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

    //crud
    private Optional<Airline> findById(Long id) {
        return repository.findById(id);
    }

    public AirlineDto getById(Long id) {
        Airline entity = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        return mapper.toDto(entity);
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
        Airline entity = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        Airline updated = (Airline)entity.updateFields(mapper.toEntity(dto));
        return mapper.toDto(repository.save(updated));
    }

    @Transactional
    public void deleteById(Long id) {
        Airline entity = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        repository.delete(entity);
    }
}
