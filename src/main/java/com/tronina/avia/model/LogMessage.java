package com.tronina.avia.model;

import com.tronina.avia.model.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "log")
public class LogMessage extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "event", nullable = false)
    private LogEvent event;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "time")
    private Timestamp time;

    @Override
    public BaseEntity updateFields(BaseEntity from) {
        return from;
    }
}
