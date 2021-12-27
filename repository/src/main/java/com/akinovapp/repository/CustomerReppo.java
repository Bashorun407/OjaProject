package com.akinovapp.repository;

import com.akinovapp.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerReppo extends JpaRepository<Customer, Long> {
    Optional<Customer> findByLastName(String lastName);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByCustomerNumber(Long customerNumber);
}
