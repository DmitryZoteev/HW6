package com.netcracker.repository;

import com.netcracker.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findCustomerByDistrict(String district);
}
