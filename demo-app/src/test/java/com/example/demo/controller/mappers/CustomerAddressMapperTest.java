package com.example.demo.controller.mappers;

import com.example.demo.controller.models.CustomerAddressDTO;
import com.example.demo.controller.models.entities.Address;
import org.junit.jupiter.api.Test;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.RequestSampleUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomerAddressMapperTest {

    private static final CustomerAddressDTO EXPECTED_MAPPED_CUSTOMER_ADDRESS_DTO = getCustomerAddressDTOSample(STREET);

    private static final Address EXPECTED_MAPPED_ADDRESS_ENTITY = getCustomerAddressEntitySample(STREET);

    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    @Test
    public void mapRequestToDTOSuccessfully(){
        assertEquals(customerAddressMapper.requestToDTO(VALID_CUSTOMER_ADDRESS), EXPECTED_MAPPED_CUSTOMER_ADDRESS_DTO);
    }

    @Test
    public void mapDTOToEntitySuccessfully(){
        assertEquals(customerAddressMapper.dtoToEntity(getCustomerAddressDTOSample(STREET)), EXPECTED_MAPPED_ADDRESS_ENTITY);
    }

}