package com.tronina.avia.repository.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.tronina.avia.model.dto.TicketFilter;
import com.tronina.avia.model.entity.*;
import com.tronina.avia.querydsl.QPredicates;
import com.tronina.avia.repository.repository.FilterTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FilterTicketRepositoryImpl implements com.tronina.avia.repository.repository.FilterTicketRepository {

    private final EntityManager entityManager;

    private static final QTicket ticket = QTicket.ticket;
    private static final QFlight flight = QFlight.flight;
    private static final QAirline airline = QAirline.airline;

    @Override
    public List<Ticket> findAllByFilter(TicketFilter filter) {
        Predicate predicate = QPredicates.builder()
                .add(filter.getAirlineName(), ticket.flight.airplane.airline.name::containsIgnoreCase)
                .build();
        return new JPAQuery<Ticket>(entityManager)
                .select(ticket)
                .from(ticket)
                .join(flight, ticket.flight)
                .join(airline, ticket.flight.airplane.airline)
                .where(predicate)
                .fetch();
//                .stream().map(row -> TicketDto)
    }

    public List<Ticket> findByRequest(TicketFilter filter) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.getAirlineName() != null) {
            predicate.or(QAirline.airline.name.equalsIgnoreCase(filter.getAirlineName()));
        }
        if (filter.getPlaneBrand() != null) {
            predicate.or(QAirplane.airplane.brand.equalsIgnoreCase(filter.getPlaneBrand()));
        }
        if (filter.getPlaneModel() != null) {
            predicate.or(QAirplane.airplane.model.equalsIgnoreCase(filter.getPlaneModel()));
        }
        if (filter.getFligthDeparture() != null) {
            predicate.or(QFlight.flight.departure.equalsIgnoreCase(filter.getFligthDeparture()));
        }
        if (filter.getFligthDestination() != null) {
            predicate.or(QFlight.flight.destination.equalsIgnoreCase(filter.getFligthDestination()));
        }
//        if (filter.getFligthDepartureTime() != null && filter.getFligthArrivalTime() != null) {
//            predicate.or(QFlight.flight.departureTime)
//        }
        if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
            predicate.or(ticket.price.between(filter.getMinPrice(), filter.getMaxPrice()));
        }

        return new JPAQuery<Ticket>(entityManager)
                .select(ticket)
                .from(ticket)
                .where(predicate)
                .fetch().stream()
                .map(row -> Ticket.builder()
//                        .firstName(row.get(user.firstName))
                        .build())
                .collect(Collectors.toList());

    }







}
