package com.tronina.avia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

    @Override
    public BaseEntity updateFields(BaseEntity from) {
        return from;
    }

    public enum Role {
        ADMIN,
        AGENT,
        SALESMAN,
        USER,
        MANAGER
        }

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}