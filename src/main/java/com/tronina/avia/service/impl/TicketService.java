package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.model.entity.Flight;
import com.tronina.avia.model.entity.Status;
import com.tronina.avia.model.entity.Ticket;
import com.tronina.avia.model.mapper.TicketMapper;
import com.tronina.avia.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final TicketMapper mapper = TicketMapper.INSTANCE;
    
    @Value("${ticket.comission}")
    private String ticketComission;

    //non auth
    public List<TicketDto> finadAllAvailableTickets(boolean status) {//todo:
        return mapper.toDtoList(repository.findAllAvailable());
    }

    //user
    @Transactional
    public TicketDto reserveTicket(Long id) {
        Optional<Ticket> optionalE = repository.findById(id);
        if (optionalE.isPresent()) {
            return mapper.toDto(changeTicketStatus(optionalE.get(), Status.RESERVED));
        } else {
            throw new RuntimeException("Error");
        }
    }

    @Transactional
    public TicketDto buyTicket(TicketDto dto) {
        Optional<Ticket> optionalE = repository.findById(dto.getId());
        if (optionalE.isPresent()) {
            return mapper.toDto(changeTicketStatus(optionalE.get(), Status.SOLD));
        } else {
            throw new RuntimeException("Error");
        }
    }

    @Transactional
    public TicketDto confirmTicket(TicketDto dto, boolean confirmed) {
        Optional<Ticket> optionalE = repository.findById(dto.getId());
        if (optionalE.isPresent()) {
            if (confirmed) {
                return mapper.toDto(changeTicketStatus(optionalE.get(), Status.RESERVATION_CONFIRMED));
            } else {
                return mapper.toDto(changeTicketStatus(optionalE.get(), Status.CREATED));
            }
        } else {
            throw new RuntimeException("Error");
        }
    }

    @Transactional
    protected Ticket changeTicketStatus(Ticket entity, Status status) {
//            entity.setCustomer();
        switch (status) {
            case RESERVED:
                entity.setStatus(Status.RESERVED);
                break;
            case RESERVATION_CONFIRMED:
                entity.setStatus(Status.RESERVATION_CONFIRMED);
                break;
            case SOLD:
                if (entity.getStatus() == Status.RESERVATION_CONFIRMED) {
                    entity.setStatus(Status.SOLD);
                }
                break;
            default:
                break;
        }
        return repository.save(entity);
    }


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

    //AGENT
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
