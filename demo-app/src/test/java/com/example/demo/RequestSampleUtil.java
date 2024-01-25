package com.example.demo;

import com.example.demo.models.CustomerAddress;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;

public class RequestSampleUtil {

    public static final String MISSING_EMAIL_ADDRESS = null;
    public static final CustomerAddress MISSING_ADDRESS = null;

    public static final CustomerAddress EMPTY_CUSTOMER_ADDRESS = new CustomerAddress();
    public static final String VALID_CUSTOMER_EMAIL = "valid@email.com";

    public static final CustomerAddress VALID_CUSTOMER_ADDRESS = new CustomerAddress("Dummy Street", "61", "7222666", "Iasi", "Romania");
    public static final String CUSTOMER_NAME = "Catalin";
    public static final String CUSTOMER_LAST_NAME = "Moisa";
    public static final int CUSTOMER_AGE = 34;

    public static CustomerRequest getCustomerRequestSample(String email, CustomerAddress address) {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFirstName(CUSTOMER_NAME);
        customerRequest.setLastName(CUSTOMER_LAST_NAME);
        customerRequest.setAge(CUSTOMER_AGE);
        customerRequest.setEmail(email);
        customerRequest.setCurrentLivingAddress(address);
        return customerRequest;
    }

    public static CustomerResponse getCustomerResponseSample(String email, CustomerAddress address) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(CUSTOMER_NAME);
        customerResponse.setFirstName(CUSTOMER_NAME);
        customerResponse.setLastName(CUSTOMER_LAST_NAME);
        customerResponse.setAge(CUSTOMER_AGE);
        customerResponse.setEmail(email);
        customerResponse.setCurrentLivingAddress(address);
        return customerResponse;
    }
}
