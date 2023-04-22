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
//@EqualsAndHashCode(callSuper = false, of = "number")
@Table(name = "reservation")
@Entity
public class TicketReserve extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "expired_time")
    private LocalDateTime expiredAt;

}
