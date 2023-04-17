package com.tronina.avia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Customer extends BaseEntity {

    @Override
    public BaseEntity updateFields(BaseEntity from) {
        return from;
    }

    public enum Role {
        ADMIN,
        AGENT,
        SALESMAN,
        USER
        }

    private String email;
    private String hashPassword;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}