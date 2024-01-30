package com.example.demo.service;

import com.example.demo.controller.mappers.CustomerAddressMapper;
import com.example.demo.controller.mappers.CustomerMapper;
import com.example.demo.controller.models.CustomerAddressDTO;
import com.example.demo.controller.models.CustomerDTO;
import com.example.demo.controller.models.entities.Address;
import com.example.demo.controller.models.entities.Customer;
import com.example.demo.models.CustomerResponse;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.demo.RequestSampleUtil.*;
import static com.example.demo.RequestSampleUtil.getCustomerAddressEntitySample;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private static final CustomerResponse EXPECTED_DB_CUSTOMER = getCustomerResponseSample(VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS);

    private static final CustomerDTO EXPECTED_CUSTOMER_DTO = getCustomerDTOSample(MISSING_ID, VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS);

    private static final CustomerDTO EXPECTED_PERSISTED_CUSTOMER_DTO = getCustomerDTOSample(VALID_ID, VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS);

    private static final Address EXPECTED_CUSTOMER_ADDRESS_ENTITY = getCustomerAddressEntitySample(STREET);

    private static final Address EXPECTED_UPDATED_CUSTOMER_ADDRESS_ENTITY = getCustomerAddressEntitySample(NEW_STREET);

    private static final CustomerAddressDTO EXPECTED_CUSTOMER_ADDRESS_DTO = getCustomerAddressDTOSample(STREET);

    private static final CustomerAddressDTO EXPECTED_UPDATED_CUSTOMER_ADDRESS_DTO = getCustomerAddressDTOSample(NEW_STREET);
    private static final Customer EXPECTED_CUSTOMER_ENTITY = getCustomerEntitySample(MISSING_ID, VALID_CUSTOMER_EMAIL, EXPECTED_CUSTOMER_ADDRESS_ENTITY);

    private static final Customer EXPECTED_UPDATED_CUSTOMER_ENTITY = getCustomerEntitySample(VALID_ID, VALID_CUSTOMER_EMAIL, EXPECTED_UPDATED_CUSTOMER_ADDRESS_ENTITY);

    private static final Customer EXPECTED_PERSISTED_CUSTOMER_ENTITY = getCustomerEntitySample(VALID_ID, VALID_CUSTOMER_EMAIL, EXPECTED_CUSTOMER_ADDRESS_ENTITY);

    private static final Customer EXPECTED_EMAIL_PERSISTED_CUSTOMER_ENTITY = getCustomerEntitySample(VALID_ID, VALID_UPDATED_CUSTOMER_EMAIL, EXPECTED_CUSTOMER_ADDRESS_ENTITY);

    private static final CustomerDTO EXPECTED_PERSISTED_UPDATED_CUSTOMER_DTO = getCustomerDTOSample(VALID_ID, VALID_CUSTOMER_EMAIL, getUpdatedCustomerAddress(NEW_STREET));
    private static final CustomerDTO EXPECTED_EMAIL_UPDATED_CUSTOMER_DTO = getCustomerDTOSample(VALID_ID, VALID_UPDATED_CUSTOMER_EMAIL, getUpdatedCustomerAddress(NEW_STREET));


    private static final CustomerResponse EXPECTED_DB_UPDATED_CUSTOMER_ADDRESS = getCustomerResponseSample(VALID_UPDATED_CUSTOMER_EMAIL, VALID_UPDATED_CUSTOMER_ADDRESS);

    private static final CustomerResponse EXPECTED_DB_UPDATED_CUSTOMER_EMAIL = getCustomerResponseSample(VALID_UPDATED_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS);

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerAddressMapper customerAddressMapper;
    private CustomerService customerService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository, customerMapper, customerAddressMapper);
    }

    @Test
    @Order(0)
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
    @Order(3)
    public void findByNamesSuccessfully() {
        when(customerRepository.findByFirstNameOrLastName("Vasile", "Moisa")).thenReturn(List.of(EXPECTED_PERSISTED_CUSTOMER_ENTITY));
        when(customerMapper.entityToDTO(EXPECTED_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_DTO);
        List<CustomerDTO> customerByFirstName = customerService.findByNames("Vasile", "Moisa");
        assertIterableEquals(customerByFirstName, List.of(EXPECTED_PERSISTED_CUSTOMER_DTO));
    }

    @Test
    @Order(3)
    public void findByLastNameSuccessfully() {
        when(customerRepository.findByFirstNameOrLastName(null, "Moisa")).thenReturn(List.of(EXPECTED_PERSISTED_CUSTOMER_ENTITY));
        when(customerMapper.entityToDTO(EXPECTED_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_DTO);
        List<CustomerDTO> customerByFirstName = customerService.findByNames(null, "Moisa");
        assertIterableEquals(customerByFirstName, List.of(EXPECTED_PERSISTED_CUSTOMER_DTO));
    }

    @Test
    @Order(2)
    public void findByFirstNameSuccessfully() {
        when(customerRepository.findByFirstNameOrLastName("Catalin", null)).thenReturn(List.of(EXPECTED_PERSISTED_CUSTOMER_ENTITY));
        when(customerMapper.entityToDTO(EXPECTED_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_DTO);
        List<CustomerDTO> customerByFirstName = customerService.findByNames("Catalin", null);
        assertIterableEquals(customerByFirstName, List.of(EXPECTED_PERSISTED_CUSTOMER_DTO));
    }

    @Test
    @Order(1)
    public void getAllCustomersSuccessfully() {
        when(customerRepository.findAll()).thenReturn(List.of(EXPECTED_PERSISTED_CUSTOMER_ENTITY));
        when(customerMapper.entityToDTO(EXPECTED_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_DTO);
        List<CustomerDTO> customers = customerService.getAllCustomers();
        assertIterableEquals(customers, List.of(EXPECTED_PERSISTED_CUSTOMER_DTO));

    }

    @Test
    public void updateCustomerAddressSuccessfully() {
        when(customerMapper.dtoToEntity(EXPECTED_PERSISTED_CUSTOMER_DTO)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_ENTITY);
        when(customerAddressMapper.dtoToEntity(EXPECTED_UPDATED_CUSTOMER_ADDRESS_DTO)).thenReturn(EXPECTED_UPDATED_CUSTOMER_ADDRESS_ENTITY);
        when(customerRepository.save(EXPECTED_UPDATED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_UPDATED_CUSTOMER_ENTITY);
        when(customerMapper.entityToDTO(EXPECTED_UPDATED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_PERSISTED_UPDATED_CUSTOMER_DTO);
        assertEquals(customerService.updateCustomerAddress(EXPECTED_PERSISTED_CUSTOMER_DTO, EXPECTED_UPDATED_CUSTOMER_ADDRESS_DTO), EXPECTED_PERSISTED_UPDATED_CUSTOMER_DTO);
    }

    @Test
    public void updateCustomerEmailSuccessfully() {
        when(customerMapper.dtoToEntity(EXPECTED_PERSISTED_CUSTOMER_DTO)).thenReturn(EXPECTED_PERSISTED_CUSTOMER_ENTITY);
        when(customerRepository.save(EXPECTED_EMAIL_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_EMAIL_PERSISTED_CUSTOMER_ENTITY);
        when(customerMapper.entityToDTO(EXPECTED_EMAIL_PERSISTED_CUSTOMER_ENTITY)).thenReturn(EXPECTED_EMAIL_UPDATED_CUSTOMER_DTO);
        assertEquals(customerService.updateCustomerEmail(EXPECTED_PERSISTED_CUSTOMER_DTO, VALID_UPDATED_CUSTOMER_EMAIL), EXPECTED_EMAIL_UPDATED_CUSTOMER_DTO);
    }

}