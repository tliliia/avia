package com.tronina.avia.repository;

import com.tronina.avia.model.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersRepository extends BaseRepository<Customer> {

    Optional<Customer> findByEmail(String email);
}
