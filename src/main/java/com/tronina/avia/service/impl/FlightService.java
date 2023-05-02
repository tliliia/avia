package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.model.dto.FlightDto;
import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.model.entity.Airplane;
import com.tronina.avia.model.entity.Flight;
import com.tronina.avia.model.mapper.FlightMapper;
import com.tronina.avia.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FlightService {

    private final FlightRepository repository;
    private final LoggingService logging;
    private final FlightMapper mapper = FlightMapper.INSTANCE;

    private final TicketService ticketService;

    public FlightDto setPlaneToFlight(Airplane plane, FlightDto dto) {
        Flight entity = mapper.toEntity(dto);
        entity.setAirplane(plane);
        return mapper.toDto(repository.save(entity));// проставилось id
    }

    //базовая стоиомость для данного рейса на данном самолете
    public List<TicketDto> buildTicketsOfFlight(Airplane plane, FlightDto dto, BigDecimal baseRate) {
        Flight entity = mapper.toEntity(dto);
        return ticketService.buildTicketsForFligth(plane.getSeats(), entity, baseRate);
    }

    //crud
    private Optional<Flight> findById(Long id) {
        return repository.findById(id);
    }

    public FlightDto getById(Long id) {
        Optional<Flight> optionalE = repository.findById(id);
        if (optionalE.isPresent()) {
            return mapper.toDto(optionalE.get());
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    public List<FlightDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Transactional
    public FlightDto create(FlightDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Transactional
    public FlightDto update(Long id, FlightDto dto) {
        Optional<Flight> originalEntity = findById(id);
        if (originalEntity.isPresent()) {
            Flight updated = (Flight) originalEntity.get().updateFields(mapper.toEntity(dto));
            return mapper.toDto(repository.save(updated));
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    @Transactional
    public void delete(FlightDto dto) {
        Flight entity = mapper.toEntity(dto);
        repository.delete(entity);
        logging.logDelete(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Flight> optionalE = findById(id);
        if (optionalE.isPresent()) {
            repository.delete(optionalE.get());
        } else {
            throw new NotFoundEntityException(id);
        }
    }

}
