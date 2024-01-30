package com.example.demo.controller.mappers;

import com.example.demo.controller.models.CustomerDTO;
import com.example.demo.controller.models.entities.Customer;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    Customer requestToEntity(CustomerRequest customer);

    Customer dtoToEntity(CustomerDTO customer);

    @Mapping(target = "id", ignore = true)
    CustomerDTO requestToDTO(CustomerRequest customer);

    CustomerDTO entityToDTO(Customer customer);

    CustomerResponse dtoToResponse(CustomerDTO customerDTO);


}
