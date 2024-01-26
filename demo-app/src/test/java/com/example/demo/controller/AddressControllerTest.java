package com.example.demo.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.demo.RequestSampleUtil.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerTest {

    private static final String BASE_API = "/api/v1";

    private static final String VALID_CUSTOMER_REQUEST_PAYLOAD = getCustomerRequestPayload();

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Order(0)
    void setup() throws Exception {
        this.mockMvc.perform(delete(BASE_API + "/customers"))
                .andDo(print())
                .andExpect(status().isOk());
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
    @Order(2)
    void successfullyUpdateAddress() throws Exception {
        this.mockMvc.perform(put(BASE_API + "/customer/{id}/address", "Catalin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getCustomerAddressPayload()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(getUpdateAddressCustomerResponsePayload())));


    }
    @Test
    @Order(3)
    void successfullyRetrieveCustomer() throws Exception {
        this.mockMvc.perform(get(BASE_API + "/customer/Catalin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(getUpdateAddressCustomerResponsePayload())));

    }

    @Test
    @Order(4)
    void successfullyUpdateCustomerEmail() throws Exception {
        this.mockMvc.perform(put(BASE_API + "/customer/{id}/address/email/{email}", "Catalin", "secondemail@test.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(getUpdateAddressAndEmailCustomerResponsePayload())));
    }

    @Test
    @Order(5)
    void successfullyRetrieveUpdatedCustomer() throws Exception {
        this.mockMvc.perform(get(BASE_API + "/customer/Catalin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(getUpdateAddressAndEmailCustomerResponsePayload())));

    }

}