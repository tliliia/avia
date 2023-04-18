package com.tronina.avia.service.impl;

import com.tronina.avia.exception.NotFoundEntityException;
import com.tronina.avia.model.dto.TicketDto;
import com.tronina.avia.model.entity.Customer;
import com.tronina.avia.model.entity.Flight;
import com.tronina.avia.model.entity.Status;
import com.tronina.avia.model.entity.Ticket;
import com.tronina.avia.model.mapper.TicketMapper;
import com.tronina.avia.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketRepository repository;
    private final LoggingService logging;
    private final ReservationService reservationService;
    private final CustomerService customerService;
    private final OrderService orderService;

    private final TicketMapper mapper = TicketMapper.INSTANCE;


    /**
     * Неавторизованные пользователи могут только просматривать доступные билеты.
     * @param actualDate
     * @return
     */
    public List<TicketDto> findAllAvailableTickets(LocalDateTime actualDate) {
        return mapper.toDtoList(repository.findAllAvailableOnDate(actualDate));
    }

    //AGENT
    /**
     * Проданные билеты не должны отображаться в списке у представителя,
     * если в запросе не был передан соответствующий флаг
     * @param airlineName название авиакомпании представителя
     * @param showSold
     * @return
     */
    public List<TicketDto>finadAllForAgent(String airlineName, boolean showSold) {
        List<Status> values = new ArrayList<>(Arrays.asList(Status.values()));
        if (!showSold) {
            values.removeIf(v -> v.equals(Status.SOLD));
        }
        return mapper.toDtoList(repository.findAllAvailable(airlineName, values));
    }

    /**
     * Представитель авиакомпании - может смотреть статистику продаж своей авиакомпании.
     * @param name название авиакомпании
     * @return
     */
    public Long countTicketsFromDeparture(String name) {
        return repository.countTicketsFromDeparture(name);
    }

    public Long countTicketsFromAirline(String name) {
        return repository.countTicketsFromAirline(name);
    }

    public Long countAvgOfComission() {//TODO: orderService на теперь в заказе
        return repository.countAvgOfComission();
    }

    //user
    //Покупатель - может забронировать билет, но не может купить до подтверждения кассиром
    @Transactional
    public void doReservation(Long id, Customer customer) {
        Ticket ticket = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
        reservationService.doReservation(ticket, customer);
    }

    //Кассир - может продавать билеты, а также снимать статус “забронирован”
    @Transactional
    public TicketDto confirmReservation(Long id, boolean confirmed) {
//        Ticket ticket = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
//            if (confirmed) {
//                return mapper.toDto(changeTicketStatus(optionalE.get(), Status.RESERVATION_CONFIRMED));
//            } else {
//                return mapper.toDto(changeTicketStatus(optionalE.get(), Status.CREATED));
//            }
        return null;
    }

    @Transactional
    public void makeOrder(Long id, Customer customer, String promo) {
        Ticket ticket = repository.findById(id).orElseThrow(() -> new NotFoundEntityException(id));
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
