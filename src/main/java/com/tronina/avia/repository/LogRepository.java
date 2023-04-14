package com.tronina.avia.repository;

import com.tronina.avia.model.LogMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends BaseRepository<LogMessage> {
}
