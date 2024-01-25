package com.example.demo.controller;

import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.demo.RequestSampleUtil.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {

    private static final String BASE_API = "/api/v1";

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final CustomerRequest VALID_CUSTOMER_REQUEST = getCustomerRequestSample(VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS);
    private static final String VALID_CUSTOMER_REQUEST_PAYLOAD = getCustomerRequestPayload();

    private static final CustomerResponse CUSTOMER_RESPONSE = getCustomerResponseSample(VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_ADDRESS);

    @Test
    void successfullyGetAllCustomers() throws Exception {
        this.mockMvc.perform(get(BASE_API + "/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(getAllCustomersExpectedPayload())));
    }

    @Test
    void successfullyFindByName() throws Exception {
        this.mockMvc.perform(get(BASE_API + "/customer/find?first_name=Catalin&last_name=Test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(getCustomerResponsePayload())));
    }

    @Test
    void successfullyFindByLastName() throws Exception {
        this.mockMvc.perform(get(BASE_API + "/customer/find?last_name=Moisa"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(getCustomerResponsePayload())));
    }

    @Test
    void successfullyFindByFirstName() throws Exception {
        this.mockMvc.perform(get(BASE_API + "/customer/find?first_name=Catalin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(getCustomerResponsePayload())));
    }

    @Test
    void successfullyFindByNamesNotMatching() throws Exception {
        this.mockMvc.perform(get(BASE_API + "/customer/find?first_name=CCatalin&last_name=MMoisa"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    @Order(1)
    void successfullyCreateCustomer() throws Exception {
        this.mockMvc.perform(post(BASE_API + "/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_CUSTOMER_REQUEST_PAYLOAD))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(getCustomerResponsePayload())));
    }

    @Test
    void successfullyRetrieveCustomer() throws Exception {
        this.mockMvc.perform(get(BASE_API + "/customer/Catalin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(getCustomerResponsePayload())));

    }

    @Test
    void failToRetrieveUnknownCustomer() throws Exception {
        this.mockMvc.perform(get(BASE_API + "/customer/CCatalin"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createCustomerWithMissingRequired() throws Exception {
        this.mockMvc.perform(post(BASE_API + "/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCustomerRequestSample(MISSING_EMAIL_ADDRESS, MISSING_ADDRESS))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomerWithEmptyRequiredAddress() throws Exception {
        this.mockMvc.perform(post(BASE_API + "/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCustomerRequestSample(MISSING_EMAIL_ADDRESS, EMPTY_CUSTOMER_ADDRESS))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    private static String getCustomerRequestPayload() {
        return objectMapper.writeValueAsString(VALID_CUSTOMER_REQUEST);
    }

    @SneakyThrows
    private static String getCustomerResponsePayload() {
        return objectMapper.writeValueAsString(CUSTOMER_RESPONSE);
    }

    @SneakyThrows
    private static String getAllCustomersExpectedPayload() {
        return objectMapper.writeValueAsString(List.of(CUSTOMER_RESPONSE));
    }


}