package com.example.demo.controller;

import com.example.demo.mappers.CustomerAddressMapper;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.models.CustomerAddress;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;
import com.example.demo.service.CustomerService;
import com.example.demo.validations.CustomerRequestValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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

    private final CustomerMapper customerMapper;

    private final CustomerAddressMapper customerAddressMapper;

    private final CustomerRequestValidator customerRequestValidator;

    @PutMapping("/customer/{id}/address")
    ResponseEntity<CustomerResponse> updateCustomerAddress(@RequestBody @Valid CustomerAddress customerAddress,
                                                           @PathVariable Long id) {
        log.info("Received request to update customer address for customer id: {}", id);
        return customerService.findCustomerById(id)
                .map(cust -> {
                    if (customerRequestValidator.isValid(CustomerRequest.builder()
                            .email(cust.getEmail())
                            .currentLivingAddress(customerAddress)
                            .build(), null)) {
                        log.info("Address requirement to either have email or all address fields met.");
                        return ResponseEntity.ok(customerMapper.dtoToResponse(
                                customerService.updateCustomerAddress(cust,
                                        customerAddressMapper.requestToDTO(customerAddress))));
                    }
                    log.info("Address requirement to either have email or all address fields were not met.");
                    throw new IllegalArgumentException("Email or all address fields are mandatory.");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/customer/{id}/address/email/{email}")
    ResponseEntity<CustomerResponse> updateCustomerEmail(@PathVariable Long id, @Valid @PathVariable @Email String email) {
        return customerService.findCustomerById(id)
                .map(cust ->
                        ResponseEntity.ok(customerMapper.dtoToResponse(
                                customerService.updateCustomerEmail(cust, email))))
                .orElse(ResponseEntity.notFound().build());
    }


}
