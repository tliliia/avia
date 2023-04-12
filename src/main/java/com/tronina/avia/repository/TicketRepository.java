package com.tronina.avia.repository;

import com.tronina.avia.entity.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
