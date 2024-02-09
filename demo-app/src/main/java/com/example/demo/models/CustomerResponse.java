package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    private String email;

    private CustomerAddress currentLivingAddress;
}
