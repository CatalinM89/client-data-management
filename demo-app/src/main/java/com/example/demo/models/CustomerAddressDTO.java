package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerAddressDTO {

    private String street;

    private String houseNumber;

    private String postalCode;

    private String city;

    private String country;

}
