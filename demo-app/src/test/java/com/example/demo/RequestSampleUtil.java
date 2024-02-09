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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RequestSampleUtil {

    public static final String MISSING_EMAIL_ADDRESS = null;
    public static final CustomerAddress MISSING_ADDRESS = null;

    public static final String STREET = "Soseaua Nationala";

    public static final String NEW_STREET = "Palas Campus";

    public static final String HOUSE_NUMBER = "61";

    public static final String ZIP_CODE = "7222666";
    public static final String CITY = "Iasi";

    public static final String COUNTRY = "Romania";
    public static final CustomerAddress EMPTY_CUSTOMER_ADDRESS = CustomerAddress.builder().build();
    public static final String VALID_CUSTOMER_EMAIL = "valid@email.com";

    public static final Long VALID_ID = 1L;

    public static final Long MISSING_ID = null;

    public static final Long MISSING_VERSION = null;

    public static final String VALID_UPDATED_CUSTOMER_EMAIL = "secondemail@test.com";

    public static final CustomerAddress VALID_CUSTOMER_ADDRESS = CustomerAddress.builder()
            .street(STREET)
            .houseNumber(HOUSE_NUMBER)
            .postalCode(ZIP_CODE)
            .city(CITY)
            .country(COUNTRY)
            .build();

    public static final CustomerAddress VALID_UPDATED_CUSTOMER_ADDRESS = CustomerAddress.builder()
            .street(NEW_STREET)
            .houseNumber(HOUSE_NUMBER)
            .postalCode(ZIP_CODE)
            .city(CITY)
            .country(COUNTRY)
            .build();
    public static final String CUSTOMER_NAME = "Catalin";
    public static final String CUSTOMER_LAST_NAME = "Moisa";
    public static final String CUSTOMER_BIRTH_DATE = "09-12-1989";
    public static final Integer CUSTOMER_AGE = 34;

    public static final LocalDate CUSTOMER_BIRTHDATE = LocalDate.parse(CUSTOMER_BIRTH_DATE, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static CustomerRequest getCustomerRequestSample(String email, CustomerAddress address) {
        return CustomerRequest.builder()
                .firstName(CUSTOMER_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .birthdate(CUSTOMER_BIRTH_DATE)
                .email(email)
                .currentLivingAddress(address)
                .build();
    }

    public static CustomerResponse getCustomerResponseSample(String email, CustomerAddress address) {
        return CustomerResponse.builder()
                .id(1L)
                .firstName(CUSTOMER_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .age(CUSTOMER_AGE)
                .email(email)
                .currentLivingAddress(address)
                .build();

    }

    public static CustomerDTO getCustomerDTOSample(Long id, String email, CustomerAddress address, Long version) {
        return CustomerDTO.builder()
                .id(id)
                .firstName(CUSTOMER_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .birthdate(CUSTOMER_BIRTHDATE)
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
                .birthdate(CUSTOMER_BIRTHDATE)
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
        return CustomerAddress.builder()
                .street(street)
                .houseNumber(HOUSE_NUMBER)
                .postalCode(ZIP_CODE)
                .city(CITY)
                .country(COUNTRY)
                .build();
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
