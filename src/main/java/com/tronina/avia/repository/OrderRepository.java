package com.tronina.avia.repository;

import com.tronina.avia.model.entity.Promo;
import com.tronina.avia.model.entity.TicketOrder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends BaseRepository<TicketOrder> {
    Optional<Promo> findByTicketId(Long id);
}
