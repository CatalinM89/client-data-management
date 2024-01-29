package com.example.demo.repository;

import com.example.demo.controller.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByFirstNameOrLastName(String firstName, String lastName);
}
