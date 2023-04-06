package com.tronina.avia.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "airplanes")
@EqualsAndHashCode(callSuper = false, of = "name")
@Table(name = "airlines")
@Entity

public class Airline extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL)
    private List<Airplane> airplanes = new ArrayList<>();
}
