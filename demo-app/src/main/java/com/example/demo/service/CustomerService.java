package com.example.demo.service;

import com.example.demo.models.CustomerAddress;
import com.example.demo.models.CustomerResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class CustomerService {

    private final List<CustomerResponse> customers = new ArrayList<>();

    //@Autowired
    // JPA repository impl for customer

    public Optional<CustomerResponse> findCustomerById(@NonNull String id) {
        log.info("Finding customer by id:{}", id);
        // Optional<CustomerEntity> jparepo.findById
        // map the entity or return empty if not found
        return customers.stream()
                .filter(cust -> cust.getId().equals(id))
                .findFirst();
    }

    public List<CustomerResponse> findByNames(@Nullable String firstName, @Nullable String lastName) {
        log.info("Finding customers data by first name:{}, and last name:{}", firstName, lastName);
        return Stream.concat(
                        (firstName == null) ? Stream.empty() : customers.stream().filter(cust -> cust.getFirstName().equals(firstName)),
                        (lastName == null) ? Stream.empty() : customers.stream().filter(cust -> cust.getLastName().equals(lastName)))
                .collect(Collectors.toList());
    }

    public boolean isCustomerRegistered(@NonNull String firstName, @NonNull String lastName) {
        log.info("Validating if the customer is already registered by first name:{}, and last name:{}", firstName, lastName);
        Optional<CustomerResponse> customer = customers.stream()
                .filter(cust -> cust.getFirstName().equals(firstName) && cust.getLastName().equals(lastName))
                .findFirst();
        return customer.isPresent();
    }

    public List<CustomerResponse> getAllCustomers() {
        return customers;
    }

    public CustomerResponse saveCustomer(@NonNull @Valid CustomerResponse customer) {
        log.info("Saving customer data for customer id:{}", customer.getId());
        customers.add(customer);
        return customer;
    }

    public CustomerResponse updateCustomerAddress(@NonNull @Valid CustomerResponse customer, @NonNull @Valid CustomerAddress address) {
        log.info("Updating customer address for customer id:{}", customer.getId());
//        CustomerResponse updateRequest = new CustomerResponse(customer.getFirstName(), customer.getFirstName(),
//                customer.getLastName(), customer.getAge());
//        updateRequest.setEmail(customer.getEmail());
//        updateRequest.setCurrentLivingAddress(address);
        customer.setCurrentLivingAddress(address);
        //return saveCustomer(customer);
        return customer;
    }

    public CustomerResponse updateCustomerEmail(@NonNull @Valid CustomerResponse customer, @NonNull String email) {
        log.info("Updating customer email for customer id:{}", customer.getId());
        customer.setEmail(email);
//        CustomerResponse updateRequest = new CustomerResponse(customer.getFirstName(), customer.getFirstName(),
//                customer.getLastName(), customer.getAge());
//        updateRequest.setEmail(email);
//        updateRequest.setCurrentLivingAddress(customer.getCurrentLivingAddress());
        //return saveCustomer(customer);
        return customer;
    }

    public void deleteAll(){
        log.error("THIS IS A TEMPORARY solution until a valid in memory db is used, ");
        customers.clear();
    }
}
