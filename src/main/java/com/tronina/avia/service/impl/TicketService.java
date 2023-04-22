package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.model.dto.CustomerDto;
import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.model.entity.*;
import com.tronina.avia.model.mapper.TicketMapper;
import com.tronina.avia.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
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
    private final CustomerService customerService;
    private final ReservationService reservationService;
    private final OrderService orderService;

    @Value("${ticket.comission}")
    private String ticketComission;

    //non auth
    public List<TicketDto> findAllAvailableTickets(boolean status) {//todo:
        return mapper.toDtoList(repository.findAllAvailable());
    }

    //user
    @Transactional
    public void doReservation(TicketDto dto, CustomerDto customerDto) {
        Ticket ticket = repository.findById(dto.getId()).orElseThrow(() -> new NotFoundEntityException(dto.getId()));
        Customer customer = customerService.loadByEmail(customerDto.getEmail()).orElseThrow(() -> new NotFoundEntityException(0l));
        reservationService.doReservation(ticket, customer);
    }

    @Transactional
    public TicketDto confirmReservation(TicketDto dto, boolean confirmed) {
        Ticket ticket = repository.findById(dto.getId()).orElseThrow(() -> new NotFoundEntityException(dto.getId()));
            if (confirmed) {
                return mapper.toDto(changeTicketStatus(optionalE.get(), Status.RESERVATION_CONFIRMED));
            } else {
                return mapper.toDto(changeTicketStatus(optionalE.get(), Status.CREATED));
            }
    }

    @Transactional
    public void makeOrder(TicketDto dto, CustomerDto customerDto, String promo) {
        Ticket ticket = repository.findById(dto.getId()).orElseThrow(() -> new NotFoundEntityException(dto.getId()));
        Customer customer = customerService.loadByEmail(customerDto.getEmail()).orElseThrow(() -> new NotFoundEntityException(0l));
        orderService.makeOrder(ticket, customer, promo);
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

    public List<TicketDto> buildTicketsForFligth(Integer seats, Flight entity, BigDecimal baseRate) {
        //crete tickets with plane.seatsNo + comission
        Set<Ticket> tickets = new HashSet<>();
        for (int i = 0; i < seats; i++) {
            Ticket ticket = Ticket.builder()
                    .number(i)
                    .flight(entity)
                    .status(Status.CREATED)
                    .price(baseRate)
                    .build();
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

    public Long countAvgOfComission() {//TODO: она теперь в заказе
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
