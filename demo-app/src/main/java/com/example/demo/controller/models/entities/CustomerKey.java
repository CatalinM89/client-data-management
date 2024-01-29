package com.example.demo.controller.models.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerKey implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;
}
