package com.tronina.avia.repository;

import com.tronina.avia.model.entity.Airplane;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplaneRepository extends BaseRepository<Airplane> {
    List<Airplane> findAllByAirlineId(Long id);
    Long countAirplanesByAirlineId(Long id);
}
