package com.tronina.avia.repository;

import com.tronina.avia.model.entity.Status;
import com.tronina.avia.model.entity.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends BaseRepository<Ticket> {
    @Query(value = "select count(t.id) from flights f left join tickets t on t.flight_id = f.id "
            + "where f.departure = :name and t.status = 'SOLD'", nativeQuery = true)
    Long countTicketsFromDeparture(@Param("name") String name);

    @Query(value = "select count(t.id) from airlines a" +
            " join airplanes p on p.airline_id = a.id" +
            " join flights f on f.airplane_id = p.id" +
            " join tickets t on t.flight_id = f.id" +
            " where t.status = 'SOLD' and a.name = :name", nativeQuery = true)
    Long countTicketsFromAirline(String name);

    @Query(value = "SELECT AVG(t.price) FROM tickets t where t.status = 'SOLD' and t.commission = true", nativeQuery = true)
    Long countAvgOfComission();

    List<Ticket> findAllByFlightId(Long id);

    @Query(value = "select * from tickets t join flights f on f.id = t.flight_id where t.status = 'CREATED' and f.arrival_time > :date",
            nativeQuery = true)
    List<Ticket> findAllAvailableOnDate(LocalDateTime date);

    @Query(value = "select * from airlines a" +
            " join airplanes p on p.airline_id = a.id" +
            " join flights f on f.airplane_id = p.id" +
            " join tickets t on t.flight_id = f.id" +
            " where a.name = :airline and t.status in (:states) ", nativeQuery = true)
    List<Ticket> findAllAvailable(String airline, List<Status> states);

}
