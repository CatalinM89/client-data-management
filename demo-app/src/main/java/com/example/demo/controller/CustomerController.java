package com.example.demo.controller;

import com.example.demo.exceptions.CustomerAlreadyRegistered;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Validated
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerMapper customerMapper;

    @PostMapping("/customer")
    ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        if (customerService.isCustomerRegistered(customerRequest.getFirstName(), customerRequest.getLastName())) {
            throw new CustomerAlreadyRegistered("Customer already registered.");
        }

        CustomerResponse customerResponse = customerMapper.dtoToResponse(customerService.saveCustomer(customerMapper.requestToDTO(customerRequest)));
        log.info("Successfully create customer with id:{}", customerResponse.getId());
        return ResponseEntity.created(URI.create("/api/v1/customer/" + customerResponse.getId())).body(customerResponse);

    }

    @GetMapping("/customers")
    List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream().map(customerMapper::dtoToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{id}")
    ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id) {
        return customerService.findCustomerById(id)
                .map(customerMapper::dtoToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/customer/find")
    ResponseEntity<List<CustomerResponse>> findCustomerByName(@RequestParam(name = "first_name", required = false) String firstName,
                                                              @RequestParam(name = "last_name", required = false) String lastName) {
        // If no search criteria provided then return an empty list
        if (firstName == null && lastName == null) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(customerService.findByNames(firstName, lastName).stream()
                .map(customerMapper::dtoToResponse)
                .collect(Collectors.toList()));

    }

}
