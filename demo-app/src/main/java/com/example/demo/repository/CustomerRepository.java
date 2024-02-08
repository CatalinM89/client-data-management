package com.example.demo.repository;

import com.example.demo.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Repository for Customer data CRUD operations
 *
 * @author Catalin Moisa
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * @param firstName the substring of the customer's first name we want to look for
     * @param lastName the substring of the customer's last name we want to look for
     * @return a set of customer who are filtered by either of the provided names
     */
    Set<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    /**
     * @param firstName customer's first name
     * @param lastName customer's last name
     * @return a customer with exact match for the names or an Optional.empty()
     */
    Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
