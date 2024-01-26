package com.example.demo.controller;

import com.example.demo.models.CustomerAddress;
import com.example.demo.models.CustomerResponse;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Validated
@Slf4j
public class AddressController {

    private final CustomerService customerService;

    @PutMapping("/customer/{id}/address")
    ResponseEntity<CustomerResponse> updateCustomerAddress(@RequestBody @Valid CustomerAddress customerAddress, @PathVariable String id) {
        log.info("Received request to update customer address for customer id: {}", id);
        return customerService.findCustomerById(id)
                .map(cust -> ResponseEntity.ok(customerService.updateCustomerAddress(cust, customerAddress)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/customer/{id}/address/email/{email}")
    ResponseEntity<CustomerResponse> updateCustomerEmail(@PathVariable String id, @PathVariable String email) {
        return customerService.findCustomerById(id)
                .map(cust -> ResponseEntity.ok(customerService.updateCustomerEmail(cust, email)))
                .orElse(ResponseEntity.notFound().build());
    }



}
