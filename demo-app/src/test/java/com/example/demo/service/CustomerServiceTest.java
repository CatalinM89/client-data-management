package com.example.demo.service;

import com.example.demo.mappers.CustomerAddressMapper;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.models.CustomerAddressDTO;
import com.example.demo.models.CustomerDTO;
import com.example.demo.models.entities.Address;
import com.example.demo.models.entities.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.demo.RequestSampleUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private static final CustomerDTO EXPECTED_CUSTOMER_DTO = getCustomerDTOSample(MISSING_ID, VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS, MISSING_VERSION);

    private static final CustomerDTO EXPECTED_PERSISTED_CUSTOMER_DTO = getCustomerDTOSample(VALID_ID, VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS, MISSING_VERSION);

    private static final Address EXPECTED_CUSTOMER_ADDRESS_ENTITY = getCustomerAddressEntitySample(STREET);

    private static final Address EXPECTED_UPDATED_CUSTOMER_ADDRESS_ENTITY = getCustomerAddressEntitySample(NEW_STREET);

    private static final CustomerAddressDTO EXPECTED_UPDATED_CUSTOMER_ADDRESS_DTO = getCustomerAddressDTOSample(NEW_STREET);
    private static final Customer EXPECTED_CUSTOMER_ENTITY = getCustomerEntitySample(MISSING_ID, VALID_CUSTOMER_EMAIL, EXPECTED_CUSTOMER_ADDRESS_ENTITY, MISSING_VERSION);

    private static final Customer EXPECTED_PERSISTED_CUSTOMER_ENTITY = getCustomerEntitySample(VALID_ID, VALID_CUSTOMER_EMAIL, EXPECTED_CUSTOMER_ADDRESS_ENTITY, 1L);

    private static final Customer EXPECTED_EMAIL_PERSISTED_CUSTOMER_ENTITY = getCustomerEntitySample(VALID_ID, VALID_UPDATED_CUSTOMER_EMAIL, EXPECTED_CUSTOMER_ADDRESS_ENTITY, 1L);

    private static final CustomerDTO EXPECTED_EMAIL_UPDATED_CUSTOMER_DTO = getCustomerDTOSample(VALID_ID, VALID_UPDATED_CUSTOMER_EMAIL, getUpdatedCustomerAddress(STREET), MISSING_VERSION);


    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerAddressMapper customerAddressMapper;

    private CustomerService customerService ;


    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository, customerMapper, customerAddressMapper);
    }

    @Test
    public void saveCustomerSuccessfully() {
        when(customerMapper.dtoToEntity(EXPECTED_CUSTOMER_DTO)).thenReturn(EXPECTED_CUSTOMER_ENTITY);
        when(customerRepository.save(EXPECTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_ENTITY);
        when(customerMapper.entityToDTO(EXPECTED_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_DTO);

        assertEquals(customerService.saveCustomer(EXPECTED_CUSTOMER_DTO), EXPECTED_PERSISTED_CUSTOMER_DTO);
    }

    @Test
    public void findCustomerByIdSuccessfully() {
        when(customerRepository.findById(VALID_ID)).thenReturn(Optional.of(EXPECTED_PERSISTED_CUSTOMER_ENTITY));
        when(customerMapper.entityToDTO(EXPECTED_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_DTO);
        Optional<CustomerDTO> customerById = customerService.findCustomerById(VALID_ID);
        if (customerById.isPresent()) {
            assertEquals(customerById.get(), EXPECTED_PERSISTED_CUSTOMER_DTO);
        } else {
            fail("Expecting findCustomerById to return a customer");
        }
    }

    @Test
    public void findCustomerByFakeId() {
        when(customerRepository.findById(404L)).thenReturn(Optional.empty());
        Optional<CustomerDTO> customerById = customerService.findCustomerById(404L);
        assertTrue(customerById.isEmpty());
        verifyNoInteractions(customerMapper);
    }

    @Test
    public void findByNamesSuccessfully() {
        when(customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("Vasile", "Moisa")).thenReturn(Set.of(EXPECTED_PERSISTED_CUSTOMER_ENTITY));
        when(customerMapper.entityToDTO(EXPECTED_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_DTO);
        List<CustomerDTO> customerByFirstName = customerService.findByNames("Vasile", "Moisa");
        assertIterableEquals(customerByFirstName, List.of(EXPECTED_PERSISTED_CUSTOMER_DTO));
    }

    @Test
    public void findByLastNameSuccessfully() {
        when(customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(null, "Moisa")).thenReturn(Set.of(EXPECTED_PERSISTED_CUSTOMER_ENTITY));
        when(customerMapper.entityToDTO(EXPECTED_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_DTO);
        List<CustomerDTO> customerByFirstName = customerService.findByNames(null, "Moisa");
        assertIterableEquals(customerByFirstName, List.of(EXPECTED_PERSISTED_CUSTOMER_DTO));
    }

    @Test
    public void findByFirstNameSuccessfully() {
        when(customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("Catalin", null)).thenReturn(Set.of(EXPECTED_PERSISTED_CUSTOMER_ENTITY));
        when(customerMapper.entityToDTO(EXPECTED_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_DTO);
        List<CustomerDTO> customerByFirstName = customerService.findByNames("Catalin", null);
        assertIterableEquals(customerByFirstName, List.of(EXPECTED_PERSISTED_CUSTOMER_DTO));
    }

    @Test
    public void getAllCustomersSuccessfully() {
        when(customerRepository.findAll()).thenReturn(List.of(EXPECTED_PERSISTED_CUSTOMER_ENTITY));
        when(customerMapper.entityToDTO(EXPECTED_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_DTO);
        List<CustomerDTO> customers = customerService.getAllCustomers();
        assertIterableEquals(customers, List.of(EXPECTED_PERSISTED_CUSTOMER_DTO));

    }

    @Test
    public void updateCustomerAddressSuccessfully() {
        CustomerDTO customerDTOSample = getCustomerDTOSample(VALID_ID, VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS, MISSING_VERSION);
        CustomerDTO updatedTest = getCustomerDTOSample(VALID_ID, VALID_CUSTOMER_EMAIL, VALID_UPDATED_CUSTOMER_ADDRESS, MISSING_VERSION);
        Customer mappedEntity = getCustomerEntitySample(VALID_ID, VALID_CUSTOMER_EMAIL, EXPECTED_CUSTOMER_ADDRESS_ENTITY, MISSING_VERSION);
        Customer updatedEntity= getCustomerEntitySample(VALID_ID, VALID_CUSTOMER_EMAIL, EXPECTED_UPDATED_CUSTOMER_ADDRESS_ENTITY, MISSING_VERSION);

        when(customerMapper.dtoToEntity(customerDTOSample)).thenReturn(mappedEntity);
        when(customerAddressMapper.dtoToEntity(EXPECTED_UPDATED_CUSTOMER_ADDRESS_DTO)).thenReturn(EXPECTED_UPDATED_CUSTOMER_ADDRESS_ENTITY);
        when(customerRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(customerMapper.entityToDTO(updatedEntity)).thenReturn(updatedTest);
        assertEquals(customerService.updateCustomerAddress(customerDTOSample, EXPECTED_UPDATED_CUSTOMER_ADDRESS_DTO), updatedTest);
    }

    @Test
    public void updateCustomerEmailSuccessfully() {
        when(customerMapper.dtoToEntity(EXPECTED_PERSISTED_CUSTOMER_DTO)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_ENTITY);
        when(customerMapper.entityToDTO(EXPECTED_EMAIL_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_EMAIL_UPDATED_CUSTOMER_DTO);
        when(customerRepository.save(EXPECTED_EMAIL_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_EMAIL_PERSISTED_CUSTOMER_ENTITY);
        assertEquals(customerService.updateCustomerEmail(EXPECTED_PERSISTED_CUSTOMER_DTO, VALID_UPDATED_CUSTOMER_EMAIL), EXPECTED_EMAIL_UPDATED_CUSTOMER_DTO);
    }

}