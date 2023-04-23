package com.tronina.avia.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    @Override
    public BaseEntity updateFields(BaseEntity from) {
        if (from instanceof Flight) {
            this.setDeparture(((Flight) from).getDeparture());
            this.setDestination(((Flight) from).getDestination());
            this.setDepartureTime(((Flight) from).getDepartureTime());
            this.setArrivalTime(((Flight) from).getArrivalTime());
        }
        return this;
    }
}
