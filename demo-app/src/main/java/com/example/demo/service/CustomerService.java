package com.example.demo.service;

import com.example.demo.mappers.CustomerAddressMapper;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.models.CustomerAddressDTO;
import com.example.demo.models.CustomerDTO;
import com.example.demo.models.entities.Customer;
import com.example.demo.repository.CustomerRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A service responsible for interacting with the DB repository and mapping the entities to DTOs
 * @author Catalin Moisa
 */
@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final CustomerAddressMapper customerAddressMapper;

    /**
     * @param id required parameter to query a customer by id
     * @return a mapped dto representing the entity or Optional.empty() if customer not found in the DB by id
     */
    public Optional<CustomerDTO> findCustomerById(@NonNull Long id) {
        log.info("Finding customer by id:{}", id);
        return customerRepository.findById(id)
                .map(customerMapper::entityToDTO);
    }

    /**
     * @param firstName case-insensitive full or partial customer's first name
     * @param lastName case-insensitive full or partial customer's last name
     * @return the list of customers that match either of the names
     */
    public List<CustomerDTO> findByNames(@Nullable String firstName, @Nullable String lastName) {
        log.info("Finding customers data by first name:{}, and last name:{}", firstName, lastName);
        return customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName)
                .stream()
                .map(customerMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * @param firstName case-sensitive customer's first name
     * @param lastName case-sensitive customer's last name
     * @return if an exact match of both names exists in the database
     */
    public boolean isCustomerRegistered(@NonNull String firstName, @NonNull String lastName) {
        log.info("Validating if the customer is already registered by first name:{}, and last name:{}", firstName, lastName);
        return customerRepository.findByFirstNameAndLastName(firstName, lastName).isPresent();
    }

    /**
     * @return all the customers from the database
     */
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Persist operation executed in a transactional scope
     * @param customer a customer dto having all the mandatory fields and constrains applied to them
     * @return a customer dto that has the id and entity version populated as well after being persisted in the DB
     */
    @Transactional
    public CustomerDTO saveCustomer(@NonNull @Valid CustomerDTO customer) {
        log.info("Saving customer data for customer first name::{}", customer.getFirstName());
        return customerMapper.entityToDTO(customerRepository.save(customerMapper.dtoToEntity(customer)));
    }

    /**
     * Update operation executed in a transactional scope
     * @param customer a customer dto having all the mandatory fields and constrains applied to them
     * @param address a customer address dto having all the mandatory fields and constrains applied to them
     * @return a customer dto that has the id and the updated entity version populated after being updated in the DB
     */
    @Transactional
    public CustomerDTO updateCustomerAddress(@NonNull @Valid CustomerDTO customer, @NonNull @Valid CustomerAddressDTO address) {
        log.info("Updating customer address for customer id:{}", customer.getId());
        Customer updatedCustomer = customerMapper.dtoToEntity(customer);
        updatedCustomer.setCurrentLivingAddress(customerAddressMapper.dtoToEntity(address));
        return customerMapper.entityToDTO(customerRepository.save(updatedCustomer));
    }

    /**
     * Update operation executed in a transactional scope
     * @param customer a customer dto having all the mandatory fields and constrains applied to them
     * @param email the new customer email that we want updated in the DB
     * @return  a customer dto that has the id and the updated entity version populated after being updated in the DB
     */
    @Transactional
    public CustomerDTO updateCustomerEmail(@NonNull @Valid CustomerDTO customer, @NonNull @Valid @Email String email) {
        log.info("Updating customer email for customer id:{}", customer.getId());
        Customer updatedCustomer = customerMapper.dtoToEntity(customer);
        updatedCustomer.setEmail(email);
        return customerMapper.entityToDTO(customerRepository.save(updatedCustomer));
    }

}
