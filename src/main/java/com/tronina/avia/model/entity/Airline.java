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
@ToString(exclude = "airplanes")
@EqualsAndHashCode(callSuper = false, of = "name")
@Table(name = "airlines")
@Entity

public class Airline extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL)
    private Set<Airplane> airplanes = new HashSet<>();

    @Override
    public BaseEntity updateFields(BaseEntity from) {
        if (from instanceof Airline) {
            this.setName(((Airline) from).getName());
        }
        return this;
    }
}
