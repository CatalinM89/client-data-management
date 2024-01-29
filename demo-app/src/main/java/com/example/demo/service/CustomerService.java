package com.example.demo.service;

import com.example.demo.controller.mappers.CustomerAddressMapper;
import com.example.demo.controller.mappers.CustomerMapper;
import com.example.demo.controller.models.CustomerAddressDTO;
import com.example.demo.controller.models.CustomerDTO;
import com.example.demo.models.CustomerAddress;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;
import com.example.demo.repository.CustomerRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CustomerService {

    private final List<CustomerResponse> customers = new ArrayList<>();


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
//        return Stream.concat(
//                        (firstName == null) ? Stream.empty() : customers.stream().filter(cust -> cust.getFirstName().equals(firstName)),
//                        (lastName == null) ? Stream.empty() : customers.stream().filter(cust -> cust.getLastName().equals(lastName)))
//                .collect(Collectors.toList());
    }

    public boolean isCustomerRegistered(@NonNull String firstName, @NonNull String lastName) {
        log.info("Validating if the customer is already registered by first name:{}, and last name:{}", firstName, lastName);
        Optional<CustomerResponse> customer = customers.stream()
                .filter(cust -> cust.getFirstName().equals(firstName) && cust.getLastName().equals(lastName))
                .findFirst();
        return customer.isPresent();
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::entityToDTO)
                .collect(Collectors.toList());
        //return customers;
    }

    public CustomerDTO saveCustomer(@NonNull @Valid CustomerDTO customer) {
        log.info("Saving customer data for customer first name::{}", customer.getFirstName());
        return customerMapper.entityToDTO(customerRepository.save(customerMapper.dtoToEntity(customer)));
    }

    public CustomerDTO updateCustomerAddress(@NonNull @Valid CustomerDTO customer, @NonNull @Valid CustomerAddressDTO address) {
        log.info("Updating customer address for customer id:{}", customer.getId());
        customerMapper.dtoToEntity(customer)
                .setCurrentLivingAddress(customerAddressMapper.dtoToEntity(address));
        return customerMapper.entityToDTO(customerRepository.save(customerMapper.dtoToEntity(customer)));
    }

    public CustomerDTO updateCustomerEmail(@NonNull @Valid CustomerDTO customer, @NonNull String email) {
        log.info("Updating customer email for customer id:{}", customer.getId());
        customer.setEmail(email);
//        CustomerResponse updateRequest = new CustomerResponse(customer.getFirstName(), customer.getFirstName(),
//                customer.getLastName(), customer.getAge());
//        updateRequest.setEmail(email);
//        updateRequest.setCurrentLivingAddress(customer.getCurrentLivingAddress());
        //return saveCustomer(customer);
        return customer;
    }

    public void deleteAll() {
        log.error("THIS IS A TEMPORARY solution until a valid in memory db is used, ");
        customers.clear();
    }
}
