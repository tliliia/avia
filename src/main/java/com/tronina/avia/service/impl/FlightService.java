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

    //базовая стоиомость для данного рейса на данном самолете
    @Transactional
    public FlightDto buildFligthWithTickets(Airplane plane, FlightDto dto, BigDecimal price) {
        Flight fromDto = mapper.toEntity(dto);
        fromDto.setAirplane(plane);
        Flight entity = repository.save(fromDto);
        ticketService.buildTicketsForFligth(plane.getSeats(), entity, price);
        return mapper.toDto(entity);
    }

    //crud
    private Optional<Flight> findById(Long id) {
        return repository.findById(id);
    }

    public FlightDto getById(Long id) {
        Flight entity = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        return mapper.toDto(entity);
    }

    public List<FlightDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Transactional
    public FlightDto update(Long id, FlightDto dto) {
        Flight entity = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        Flight updated = (Flight) entity.updateFields(mapper.toEntity(dto));
        return mapper.toDto(repository.save(updated));
    }


    @Transactional
    public void deleteById(Long id) {
        Flight entity = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        repository.delete(entity);
    }

}
