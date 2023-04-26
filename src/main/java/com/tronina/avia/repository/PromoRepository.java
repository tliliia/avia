package com.tronina.avia.repository;

import com.tronina.avia.model.entity.Promo;
import com.tronina.avia.model.entity.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromoRepository extends BaseRepository<Promo> {
    Optional<Promo> findByTitle(String title);
}
