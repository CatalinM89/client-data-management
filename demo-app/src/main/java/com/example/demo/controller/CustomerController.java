package com.example.demo.controller;

import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;
import com.example.demo.service.CustomerService;
import com.example.demo.validations.EmailOrAddressValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Validated
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    private final EmailOrAddressValidator emailOrAddressValidator;


    @PostMapping("/customer")
    ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        if (!emailOrAddressValidator.isValid(customerRequest)) {
            throw new IllegalArgumentException("Missing or invalid required fields.");
            //return ResponseEntity.badRequest().body("Missing or invalid required fields.");
        }
        if (customerService.isCustomerRegistered(customerRequest.getFirstName(), customerRequest.getLastName())) {
            throw new IllegalArgumentException("Customer already registered.");
        }

        // The id will be generated by a dedicated service
        CustomerResponse response = new CustomerResponse(customerRequest.getFirstName(), customerRequest.getFirstName(),
                customerRequest.getLastName(), customerRequest.getAge());
        response.setEmail(customerRequest.getEmail());
        response.setCurrentLivingAddress(customerRequest.getCurrentLivingAddress());
        customerService.saveCustomer(response);
        return ResponseEntity.created(URI.create("/api/v1/customer/" + response.getId())).body(response);

    }

    @GetMapping("/customers")
    List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer/{id}")
    ResponseEntity<CustomerResponse> getCustomer(@PathVariable String id) {
        return customerService.findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/customer/find")
    ResponseEntity<List<CustomerResponse>> findCustomerByName(@RequestParam(name = "first_name", required = false) String firstName,
                                                              @RequestParam(name = "last_name", required = false) String lastName) {
        // If no search criteria provided then default to a bad request
        if (firstName == null && lastName == null) {
            return ResponseEntity.badRequest().build();
        }
        // What if both criteria are provided ?
        return ResponseEntity.ok(customerService.findByNames(firstName, lastName));

    }

    @DeleteMapping("/customers")
    void deleteAllCustomers(){
        log.info("With great power comes great responsibility. Wiping 'the database'... ");
        customerService.deleteAll();
    }
}
