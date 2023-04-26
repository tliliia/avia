package com.tronina.avia.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false, of = "title")
@Table(name = "promo")
@Entity
public class Promo extends BaseEntity {

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "percent", nullable = false)
    private Integer percent;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expireDate;

    @Column(name = "max_usage", nullable = false)
    private Integer maxUsage;

    @Column(name = "actual_usage")
    private Integer actualUsage;
}
