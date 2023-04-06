package com.tronina.avia.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false, of = "number")
@Table(name = "tickets")
@Entity
public class Ticket extends BaseEntity {
    /**
     * Порядковый номер в салоне самолета
     */
    @Column(name = "number")
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "commission")
    private boolean commission;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
