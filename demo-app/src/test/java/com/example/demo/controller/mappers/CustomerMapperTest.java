package com.example.demo.controller.mappers;

import com.example.demo.models.CustomerDTO;
import com.example.demo.models.entities.Customer;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.models.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.RequestSampleUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomerMapperTest {

    private static final Customer EXPECTED_CUSTOMER_ENTITY_FROM_REQUEST =
            getCustomerEntitySample(MISSING_ID, VALID_CUSTOMER_EMAIL, getCustomerAddressEntitySample(STREET), MISSING_VERSION);

    private static final Customer EXPECTED_CUSTOMER_ENTITY_FROM_DTO =
            getCustomerEntitySample(VALID_ID, VALID_CUSTOMER_EMAIL, getCustomerAddressEntitySample(STREET), MISSING_VERSION);

    private static final CustomerDTO EXPECTED_CUSTOMER_DTO_FROM_REQUEST =
            getCustomerDTOSample(MISSING_ID, VALID_CUSTOMER_EMAIL, getUpdatedCustomerAddress(STREET), MISSING_VERSION);
    private static final CustomerDTO EXPECTED_CUSTOMER_DTO_FROM_ENTITY =
            getCustomerDTOSample(VALID_ID, VALID_CUSTOMER_EMAIL, getUpdatedCustomerAddress(STREET), MISSING_VERSION);

    private static final CustomerResponse EXPECTED_CUSTOMER_RESPONSE_FROM_DTO =
            getCustomerResponseSample(VALID_CUSTOMER_EMAIL, getUpdatedCustomerAddress(STREET));


    @Autowired
    private CustomerMapper customerMapper;

    @Test
    void requestToEntitySuccessfully() {
        assertEquals(customerMapper.requestToEntity(
                        getCustomerRequestSample(VALID_CUSTOMER_EMAIL, getUpdatedCustomerAddress(STREET))),
                EXPECTED_CUSTOMER_ENTITY_FROM_REQUEST);
    }

    @Test
    void dtoToEntitySuccessfully() {
        assertEquals(customerMapper.dtoToEntity(
                        getCustomerDTOSample(VALID_ID, VALID_CUSTOMER_EMAIL, getUpdatedCustomerAddress(STREET), MISSING_VERSION)),
                EXPECTED_CUSTOMER_ENTITY_FROM_DTO);
    }

    @Test
    void requestToDTOSuccessfully() {
        assertEquals(customerMapper.requestToDTO(
                        getCustomerRequestSample(VALID_CUSTOMER_EMAIL, getUpdatedCustomerAddress(STREET))),
                EXPECTED_CUSTOMER_DTO_FROM_REQUEST);
    }

    @Test
    void entityToDTOSuccessfully() {
        assertEquals(customerMapper.entityToDTO(
                        getCustomerEntitySample(VALID_ID, VALID_CUSTOMER_EMAIL, getCustomerAddressEntitySample(STREET), MISSING_VERSION)),
                EXPECTED_CUSTOMER_DTO_FROM_ENTITY);
    }

    @Test
    void dtoToResponseSuccessfully() {
        assertEquals(customerMapper.dtoToResponse(
                        getCustomerDTOSample(VALID_ID, VALID_CUSTOMER_EMAIL, getUpdatedCustomerAddress(STREET), MISSING_VERSION)),
                EXPECTED_CUSTOMER_RESPONSE_FROM_DTO);
    }
}