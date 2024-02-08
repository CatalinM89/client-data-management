package com.example.demo;

import com.example.demo.models.CustomerAddressDTO;
import com.example.demo.models.CustomerDTO;
import com.example.demo.models.entities.Address;
import com.example.demo.models.entities.Customer;
import com.example.demo.models.CustomerAddress;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class RequestSampleUtil {

    public static final String MISSING_EMAIL_ADDRESS = null;
    public static final CustomerAddress MISSING_ADDRESS = null;

    public static final String STREET = "Soseaua Nationala";

    public static final String NEW_STREET = "Palas Campus";

    private static final String HOUSE_NUMBER = "61";

    private static final String ZIP_CODE = "7222666";
    private static final String CITY = "Iasi";

    private static final String COUNTRY = "Romania";
    public static final CustomerAddress EMPTY_CUSTOMER_ADDRESS = new CustomerAddress();
    public static final String VALID_CUSTOMER_EMAIL = "valid@email.com";

    public static final Long VALID_ID = 1L;

    public static final Long MISSING_ID = null;

    public static final Long MISSING_VERSION = null;

    public static final String VALID_UPDATED_CUSTOMER_EMAIL = "secondemail@test.com";

    public static final CustomerAddress VALID_CUSTOMER_ADDRESS = new CustomerAddress(STREET, HOUSE_NUMBER, ZIP_CODE, CITY, COUNTRY);

    public static final CustomerAddress VALID_UPDATED_CUSTOMER_ADDRESS = new CustomerAddress(NEW_STREET, HOUSE_NUMBER, ZIP_CODE, CITY, COUNTRY);
    public static final String CUSTOMER_NAME = "Catalin";
    public static final String CUSTOMER_LAST_NAME = "Moisa";
    public static final int CUSTOMER_AGE = 34;

    private static final ObjectMapper objectMapper = new ObjectMapper();

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
        customerResponse.setId(1L);
        customerResponse.setFirstName(CUSTOMER_NAME);
        customerResponse.setLastName(CUSTOMER_LAST_NAME);
        customerResponse.setAge(CUSTOMER_AGE);
        customerResponse.setEmail(email);
        customerResponse.setCurrentLivingAddress(address);
        return customerResponse;
    }

    public static CustomerDTO getCustomerDTOSample(Long id, String email, CustomerAddress address, Long version) {
        return CustomerDTO.builder()
                .id(id)
                .firstName(CUSTOMER_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .age(CUSTOMER_AGE)
                .email(email)
                .currentLivingAddress(address)
                .version(version)
                .build();
    }

    public static Customer getCustomerEntitySample(Long id, String email, Address address, Long entityVersion) {
        return Customer.builder()
                .id(id)
                .firstName(CUSTOMER_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .age(CUSTOMER_AGE)
                .email(email)
                .currentLivingAddress(address)
                .version(entityVersion)
                .build();
    }

    public static Address getCustomerAddressEntitySample(String street) {
        return Address.builder()
                .street(street)
                .houseNumber(HOUSE_NUMBER)
                .postalCode(ZIP_CODE)
                .city(CITY)
                .country(COUNTRY)
                .build();
    }

    public static CustomerAddress getUpdatedCustomerAddress(String street) {
        return new CustomerAddress(street, HOUSE_NUMBER, ZIP_CODE, CITY, COUNTRY);
    }

    public static CustomerAddressDTO getCustomerAddressDTOSample(String street) {
        return CustomerAddressDTO.builder()
                .street(street)
                .houseNumber(HOUSE_NUMBER)
                .postalCode(ZIP_CODE)
                .city(CITY)
                .country(COUNTRY)
                .build();
    }

    @SneakyThrows
    public static String getCustomerResponsePayload() {
        return objectMapper.writeValueAsString(getCustomerResponseSample(VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS));
    }

    @SneakyThrows
    public static String getUpdateAddressCustomerResponsePayload() {
        return objectMapper.writeValueAsString(getCustomerResponseSample(VALID_CUSTOMER_EMAIL, VALID_UPDATED_CUSTOMER_ADDRESS));
    }

    @SneakyThrows
    public static String getUpdateAddressAndEmailCustomerResponsePayload() {
        return objectMapper.writeValueAsString(getCustomerResponseSample(VALID_UPDATED_CUSTOMER_EMAIL, VALID_UPDATED_CUSTOMER_ADDRESS));
    }

    @SneakyThrows
    public static String getCustomerAddressPayload() {
        return objectMapper.writeValueAsString(VALID_UPDATED_CUSTOMER_ADDRESS);
    }

    @SneakyThrows
    public static String getCustomerRequestPayload() {
        return objectMapper.writeValueAsString(getCustomerRequestSample(VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS));
    }
}
