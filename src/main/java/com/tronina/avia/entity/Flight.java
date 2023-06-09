package com.tronina.avia.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false, of = {"departure","destination"})
@ToString(exclude = "tickets")
@Table(name = "flights")
@Entity
public class Flight extends BaseEntity {
    @Column(name = "departure")
    private String departure;

    @Column(name = "destination")
    private String destination;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @ManyToOne(fetch = FetchType.LAZY)//todo:
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @Builder.Default
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private Set<Ticket> tickets = new HashSet<>();
}
