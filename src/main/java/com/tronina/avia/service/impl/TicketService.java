package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.model.entity.Flight;
import com.tronina.avia.model.entity.Status;
import com.tronina.avia.model.entity.Ticket;
import com.tronina.avia.model.entity.Ticket;
import com.tronina.avia.model.mapper.TicketMapper;
import com.tronina.avia.repository.TicketRepository;
import com.tronina.avia.service.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketRepository repository;
    private final LoggingService logging;
    private final TicketMapper mapper;
    
    @Value("${ticket.comission}")
    private String ticketComission;

    private Ticket applyComission(Ticket entity) {
        Double multiplyer = 1L + Double.parseDouble(ticketComission);
        entity.setPrice(entity.getPrice().multiply(BigDecimal.valueOf(multiplyer)));
        entity.setCommission(true);
        return entity;
    }


    public List<TicketDto> buildTicketsForFligth(Integer seats, Flight entity, BigDecimal baseRate) {
        //crete tickets with plane.seatsNo + comission
        Set<Ticket> tickets = new HashSet<>();
        int randomNum = ThreadLocalRandom.current().nextInt(0, seats + 1);
        for (int i = 0; i < seats; i++) {
            Ticket ticket = Ticket.builder()
                    .number(i)
                    .flight(entity)
                    .status(Status.CREATED)
                    .price(baseRate)
                    .build();
            if (i == randomNum) {
                ticket = applyComission(ticket);
            }
            tickets.add(ticket);
        }

        tickets.forEach(t -> repository.save(t));
        return mapper.toDtoList(repository.findAllByFlightId(entity.getId()));
    }

    public Long countTicketsFromDeparture(String name) {
        return repository.countTicketsFromDeparture(name);
    }

    public Long countTicketsFromAirline(String name) {
        return repository.countTicketsFromAirline(name);
    }

    public Long countAvgOfComission() {
        return repository.countAvgOfComission();
    }

    //crud
    private Optional<Ticket> findById(Long id) {
        return repository.findById(id);
    }

    public TicketDto getById(Long id) {
        Optional<Ticket> optionalE = repository.findById(id);
        if (optionalE.isPresent()) {
            return mapper.toDto(optionalE.get());
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    public List<TicketDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Transactional
    public TicketDto create(TicketDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Transactional
    public TicketDto update(Long id, TicketDto dto) {
        Optional<Ticket> originalEntity = findById(id);
        if (originalEntity.isPresent()) {
            Ticket updated = (Ticket) originalEntity.get().updateFields(mapper.toEntity(dto));
            return mapper.toDto(repository.save(updated));
        } else {
            throw new NotFoundEntityException(id);
        }
    }

    @Transactional
    public void delete(TicketDto dto) {
        Ticket entity = mapper.toEntity(dto);
        repository.delete(entity);
        logging.logDelete(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Ticket> optionalE = findById(id);
        if (optionalE.isPresent()) {
            repository.delete(optionalE.get());
        } else {
            throw new NotFoundEntityException(id);
        }
    }

}
