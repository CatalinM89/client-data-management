package com.example.demo.models;

import com.example.demo.validations.ValidBirthDate;
import com.example.demo.validations.ValidCustomerRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ValidCustomerRequest
public class CustomerRequest {

    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty
    @JsonProperty("last_name")
    private String lastName;

    @NotEmpty
    @ValidBirthDate
    private String birthdate;

    @Email
    private String email;

    @JsonProperty("current_living_address")
    private CustomerAddress currentLivingAddress;
}
