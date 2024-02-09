package com.example.demo.validations;

import com.example.demo.models.CustomerAddress;
import com.example.demo.models.CustomerRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

/**
 * Custom validator for the customer create request, making sure there is either email or valid address
 *
 * @author Catalin Moisa
 */
@Component
public class CustomerRequestValidator implements ConstraintValidator<ValidCustomerRequest, CustomerRequest> {
    @Override
    public void initialize(ValidCustomerRequest constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CustomerRequest customerRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (!Strings.isBlank(customerRequest.getEmail())) {
            return true;
        }
        CustomerAddress address = customerRequest.getCurrentLivingAddress();
        if (address == null) {
            return false;
        }
        return !Strings.isBlank(address.getCountry()) &&
                !Strings.isBlank(address.getCity()) &&
                !Strings.isBlank(address.getStreet()) &&
                !Strings.isBlank(address.getHouseNumber()) &&
                !Strings.isBlank(address.getPostalCode());
    }
}
