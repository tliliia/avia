package com.tronina.avia.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"airplane", "flights"})
@EqualsAndHashCode(callSuper=false, of = {"brand","model"})
@Table(name = "airplanes")
@Entity
public class Airplane extends BaseEntity {

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "seats")
    private Integer seats;

    @Builder.Default
    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL)
    private Set<Flight> flights = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @Override
    public BaseEntity updateFields(BaseEntity from) {
        if (from instanceof Airplane) {
            this.setBrand(((Airplane) from).getBrand());
            this.setModel(((Airplane) from).getModel());
            this.setSeats(((Airplane) from).getSeats());
        }
        return this;
    }

}
