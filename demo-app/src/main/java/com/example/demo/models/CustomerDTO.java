package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthdate;

    private String email;

    private CustomerAddress currentLivingAddress;

    private Long version;
}
