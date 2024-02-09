package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerAddress {

    private String street;

    @JsonProperty("house_number")
    private String houseNumber;

    @JsonProperty("postal_code")
    private String postalCode;

    private String city;

    private String country;
}
