package com.example.demo.validations;

import org.junit.jupiter.api.Test;

import static com.example.demo.RequestSampleUtil.MISSING_ADDRESS;
import static com.example.demo.RequestSampleUtil.MISSING_EMAIL_ADDRESS;
import static com.example.demo.RequestSampleUtil.VALID_CUSTOMER_ADDRESS;
import static com.example.demo.RequestSampleUtil.VALID_CUSTOMER_EMAIL;
import static com.example.demo.RequestSampleUtil.getCustomerRequestSample;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailOrAddressValidatorTest {

    private final EmailOrAddressValidator emailOrAddressValidator = new EmailOrAddressValidator();

    @Test
    public void isValidWhenEmailPresent(){
        assertTrue(emailOrAddressValidator.isValid(getCustomerRequestSample(VALID_CUSTOMER_EMAIL, MISSING_ADDRESS)));
    }

    @Test
    public void isValidWhenAddressPresent(){
        assertTrue(emailOrAddressValidator.isValid(getCustomerRequestSample(MISSING_EMAIL_ADDRESS, VALID_CUSTOMER_ADDRESS)));
    }

    @Test
    public void isValidWhenEmailAndAddressPresent(){
        assertTrue(emailOrAddressValidator.isValid(getCustomerRequestSample(VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS)));
    }

    @Test
    public void isInValidWhenEmailAndAddressAbsent(){
        assertFalse(emailOrAddressValidator.isValid(getCustomerRequestSample(MISSING_EMAIL_ADDRESS, MISSING_ADDRESS)));
    }

}