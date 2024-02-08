package com.example.demo.repository;

import com.example.demo.models.entities.Address;
import com.example.demo.models.entities.Customer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.example.demo.RequestSampleUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerRepositoryTest {

    private static final Address customerAddress = getCustomerAddressEntitySample(STREET);
    private static final Customer EXPECTED_UPDATED_PERSISTED_CUSTOMER_ENTITY_SAMPLE = getCustomerEntitySample(VALID_ID, VALID_UPDATED_CUSTOMER_EMAIL, customerAddress, 1L);

    private static final Customer EXPECTED_PERSISTED_CUSTOMER_ENTITY_SAMPLE = getCustomerEntitySample(VALID_ID, VALID_CUSTOMER_EMAIL, customerAddress, 0L);
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Order(1)
    public void saveCustomerSuccessfully() {
        Customer customer = getCustomerEntitySample(MISSING_ID, VALID_CUSTOMER_EMAIL, customerAddress, MISSING_VERSION);
        Customer actualPersistedCustomer = customerRepository.save(customer);
        assertEquals(EXPECTED_PERSISTED_CUSTOMER_ENTITY_SAMPLE, actualPersistedCustomer);

        Optional<Customer> actualCustomerById = customerRepository.findById(VALID_ID);
        assertTrue(actualCustomerById.isPresent());
        assertEquals(actualCustomerById.get(), EXPECTED_PERSISTED_CUSTOMER_ENTITY_SAMPLE);

    }

    @Test
    @Order(2)
    public void updateCustomerSuccessfully() {
        Optional<Customer> customerById = customerRepository.findById(VALID_ID);
        assertTrue(customerById.isPresent());
        customerById.get().setEmail(VALID_UPDATED_CUSTOMER_EMAIL);
        customerRepository.save(customerById.get());

        Optional<Customer> updatedCustomerById = customerRepository.findById(VALID_ID);
        assertTrue(updatedCustomerById.isPresent());
        Customer expectedUpdatedCustomer = getCustomerEntitySample(VALID_ID, VALID_UPDATED_CUSTOMER_EMAIL, customerAddress, 1L);
        assertEquals(updatedCustomerById.get(), expectedUpdatedCustomer);
    }

    @Test
    public void ignoreCaseFirstNameSuccessfully() {
        Set<Customer> actualCustomerById = customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("ca", null);
        assertEquals(actualCustomerById.size(), 1);
        assertIterableEquals(actualCustomerById, Set.of(EXPECTED_UPDATED_PERSISTED_CUSTOMER_ENTITY_SAMPLE));
    }

    @Test
    public void ignoreCaseLastNameSuccessfully() {
        Set<Customer> actualCustomerById = customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(null, "mo");
        assertEquals(actualCustomerById.size(), 1);
        assertIterableEquals(actualCustomerById, Set.of(EXPECTED_UPDATED_PERSISTED_CUSTOMER_ENTITY_SAMPLE));
    }

    @Test
    public void ignoreCaseFirstAndLastNameSuccessfully() {
        Set<Customer> actualCustomerById = customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("ca", "mo");
        assertEquals(actualCustomerById.size(), 1);
        assertIterableEquals(actualCustomerById, Set.of(EXPECTED_UPDATED_PERSISTED_CUSTOMER_ENTITY_SAMPLE));
    }

    @Test
    public void missingFirstAndLastNameSuccessfully() {
        Set<Customer> actualCustomerById = customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(null, null);
        assertEquals(actualCustomerById.size(), 0);
    }


}