package com.example.demo.controller.models;

import com.example.demo.models.CustomerAddress;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    private String email;

    private CustomerAddress currentLivingAddress;
}
