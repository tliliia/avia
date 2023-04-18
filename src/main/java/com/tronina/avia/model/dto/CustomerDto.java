package com.tronina.avia.model.dto;

import com.tronina.avia.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    String email;
    String password;
    Customer.Role role;
}
