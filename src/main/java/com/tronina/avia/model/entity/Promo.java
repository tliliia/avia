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

    @Column(name = "percent")
    private Integer percent;

//    У промокода имеется срок действия, а также максимальное количество использований.
    @Column(name = "expired_at")
    private LocalDateTime expireDate;

    @Column(name = "usage_count")
    private Integer maxUsageCounter;
}
