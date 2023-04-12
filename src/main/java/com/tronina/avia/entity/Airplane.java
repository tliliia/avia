package com.tronina.avia.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<Flight> flights = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;
}
