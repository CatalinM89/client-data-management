package com.example.demo.validations;

import com.example.demo.models.CustomerAddress;
import com.example.demo.models.CustomerRequest;
import jakarta.validation.Valid;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * The business requirements mandate that the customer to provide either a valid email or an address
 */
@Service
@Validated
public class EmailOrAddressValidator  {

    public boolean isValid(@NonNull CustomerRequest request) {
        return request.getEmail() != null || (request.getCurrentLivingAddress() != null && isValidAddress(request.getCurrentLivingAddress()));
    }

    /**
    Leverage the hibernate validator to check the constraints expressed in the model contract
     */
    private boolean isValidAddress(@NonNull @Valid CustomerAddress customerAddress) {
        return true;
    }
}
