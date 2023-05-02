package com.tronina.avia.service.impl;

import com.tronina.avia.model.LogEvent;
import com.tronina.avia.model.LogMessage;
import com.tronina.avia.model.entity.BaseEntity;
import com.tronina.avia.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LoggingService {
    private final LogRepository repository;

    public void logDelete(BaseEntity entity) {
        repository.save(LogMessage.builder()
                .event(LogEvent.DELETE)
                .entityId(entity.getId())
                .entityName(entity.getClass().getName())
                .time(Timestamp.from(Instant.now()))
                .build());
    }
}
