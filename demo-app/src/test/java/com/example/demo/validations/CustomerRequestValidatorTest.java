package com.example.demo.validations;

import com.example.demo.models.CustomerAddress;
import com.example.demo.models.CustomerRequest;
import org.junit.jupiter.api.Test;

import static com.example.demo.RequestSampleUtil.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerRequestValidatorTest {

    private final CustomerRequestValidator customerRequestValidator = new CustomerRequestValidator();

    @Test
    public void validateRequest() {
        assertTrue(customerRequestValidator.isValid(CustomerRequest.builder()
                .firstName(CUSTOMER_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .birthdate(CUSTOMER_BIRTH_DATE)
                .email(VALID_CUSTOMER_EMAIL)
                .currentLivingAddress(CustomerAddress.builder()
                        .street(STREET)
                        .houseNumber(HOUSE_NUMBER)
                        .postalCode(ZIP_CODE)
                        .city(CITY)
                        .country(COUNTRY)
                        .build())
                .build(), null));
    }

    @Test
    public void validateRequestMissingEmail() {
        assertTrue(customerRequestValidator.isValid(CustomerRequest.builder()
                .firstName(CUSTOMER_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .birthdate(CUSTOMER_BIRTH_DATE)
                .currentLivingAddress(CustomerAddress.builder()
                        .street(STREET)
                        .houseNumber(HOUSE_NUMBER)
                        .postalCode(ZIP_CODE)
                        .city(CITY)
                        .country(COUNTRY)
                        .build())
                .build(), null));
    }

    @Test
    public void validateRequestEmptyEmail() {
        assertFalse(customerRequestValidator.isValid(CustomerRequest.builder()
                .firstName(CUSTOMER_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .birthdate(CUSTOMER_BIRTH_DATE)
                .email("   ")
                .build(), null));
    }

    @Test
    public void validateRequestMissingEmailAndAddressField() {
        assertFalse(customerRequestValidator.isValid(CustomerRequest.builder()
                .firstName(CUSTOMER_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .birthdate(CUSTOMER_BIRTH_DATE)
                .currentLivingAddress(CustomerAddress.builder()
                        .houseNumber(HOUSE_NUMBER)
                        .postalCode(ZIP_CODE)
                        .city(CITY)
                        .country(COUNTRY)
                        .build())
                .build(), null));
    }

    @Test
    public void validateRequestMissingEmailAndAllAddress() {
        assertFalse(customerRequestValidator.isValid(CustomerRequest.builder()
                .firstName(CUSTOMER_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .birthdate(CUSTOMER_BIRTH_DATE)
                .build(), null));
    }


}