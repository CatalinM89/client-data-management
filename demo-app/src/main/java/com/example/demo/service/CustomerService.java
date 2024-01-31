package com.example.demo.service;

import com.example.demo.mappers.CustomerAddressMapper;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.models.CustomerAddressDTO;
import com.example.demo.models.CustomerDTO;
import com.example.demo.models.entities.Customer;
import com.example.demo.repository.CustomerRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final CustomerAddressMapper customerAddressMapper;

    public Optional<CustomerDTO> findCustomerById(@NonNull Long id) {
        log.info("Finding customer by id:{}", id);
        return customerRepository.findById(id)
                .map(customerMapper::entityToDTO);
    }

    public List<CustomerDTO> findByNames(@Nullable String firstName, @Nullable String lastName) {
        log.info("Finding customers data by first name:{}, and last name:{}", firstName, lastName);
        return customerRepository.findByFirstNameOrLastName(firstName, lastName)
                .stream()
                .map(customerMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    public boolean isCustomerRegistered(@NonNull String firstName, @NonNull String lastName) {
        log.info("Validating if the customer is already registered by first name:{}, and last name:{}", firstName, lastName);
        return customerRepository.findByFirstNameAndLastName(firstName, lastName).isPresent();
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO saveCustomer(@NonNull @Valid CustomerDTO customer) {
        log.info("Saving customer data for customer first name::{}", customer.getFirstName());
        return customerMapper.entityToDTO(customerRepository.save(customerMapper.dtoToEntity(customer)));
    }

    public CustomerDTO updateCustomerAddress(@NonNull @Valid CustomerDTO customer, @NonNull @Valid CustomerAddressDTO address) {
        log.info("Updating customer address for customer id:{}", customer.getId());
        Customer updatedCustomer = customerMapper.dtoToEntity(customer);
        updatedCustomer.setCurrentLivingAddress(customerAddressMapper.dtoToEntity(address));
        return customerMapper.entityToDTO(customerRepository.save(updatedCustomer));
    }

    public CustomerDTO updateCustomerEmail(@NonNull @Valid CustomerDTO customer, @NonNull String email) {
        log.info("Updating customer email for customer id:{}", customer.getId());
        Customer updatedCustomer = customerMapper.dtoToEntity(customer);
        updatedCustomer.setEmail(email);
        return customerMapper.entityToDTO(customerRepository.save(updatedCustomer));
    }

}
