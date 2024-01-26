package com.example.demo.service;

import com.example.demo.models.CustomerResponse;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static com.example.demo.RequestSampleUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {

    private static final CustomerResponse EXPECTED_DB_CUSTOMER = getCustomerResponseSample(VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS);

    private static final CustomerResponse EXPECTED_DB_UPDATED_CUSTOMER_ADDRESS = getCustomerResponseSample(VALID_UPDATED_CUSTOMER_EMAIL, VALID_UPDATED_CUSTOMER_ADDRESS);

    private static final CustomerResponse EXPECTED_DB_UPDATED_CUSTOMER_EMAIL = getCustomerResponseSample(VALID_UPDATED_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS);
    private static final CustomerService customerService = new CustomerService();

    @BeforeAll
    public static void setup() {
        customerService.saveCustomer(EXPECTED_DB_CUSTOMER);
    }

    @Test
    public void saveCustomerSuccessfully() {
        assertEquals(customerService.saveCustomer(EXPECTED_DB_CUSTOMER), EXPECTED_DB_CUSTOMER);
    }

    @Test
    public void findCustomerByIdSuccessfully() {
        Optional<CustomerResponse> customerById = customerService.findCustomerById(EXPECTED_DB_CUSTOMER.getId());
        if (customerById.isPresent()) {
            assertEquals(customerById.get(), EXPECTED_DB_CUSTOMER);
        } else {
            fail("Expecting findCustomerById to return a customer");
        }
    }

    @Test
    public void findCustomerByFakeId() {
        Optional<CustomerResponse> customerById = customerService.findCustomerById("404");
        assertTrue(customerById.isEmpty());
    }

    @Test
    @Order(3)
    public void findByNamesSuccessfully() {
        List<CustomerResponse> customerByFirstName = customerService.findByNames("Vasile", "Moisa");
        assertIterableEquals(customerByFirstName, List.of(EXPECTED_DB_CUSTOMER));
    }

    @Test
    @Order(3)
    public void findByLastNameSuccessfully() {
        List<CustomerResponse> customerByFirstName = customerService.findByNames(null, "Moisa");
        assertIterableEquals(customerByFirstName, List.of(EXPECTED_DB_CUSTOMER));
    }

    @Test
    @Order(2)
    public void findByFirstNameSuccessfully() {
        List<CustomerResponse> customerByFirstName = customerService.findByNames("Catalin", null);
        assertIterableEquals(customerByFirstName, List.of(EXPECTED_DB_CUSTOMER));
    }

    @Test
    @Order(1)
    public void getAllCustomersSuccessfully() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        assertIterableEquals(customers, List.of(EXPECTED_DB_CUSTOMER));

    }

    @Test
    public void updateCustomerAddressSuccessfully() {
        assertEquals(customerService.updateCustomerAddress(EXPECTED_DB_CUSTOMER, VALID_UPDATED_CUSTOMER_ADDRESS), EXPECTED_DB_UPDATED_CUSTOMER_ADDRESS);
    }

    @Test
    public void updateCustomerEmailSuccessfully() {
        assertEquals(customerService.updateCustomerEmail(EXPECTED_DB_CUSTOMER, VALID_UPDATED_CUSTOMER_EMAIL), EXPECTED_DB_UPDATED_CUSTOMER_EMAIL);
    }

}