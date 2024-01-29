package com.example.demo.controller.mappers;

import com.example.demo.controller.models.entities.Customer;
import com.example.demo.controller.models.CustomerDTO;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;
import lombok.NonNull;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer requestToEntity(@NonNull CustomerRequest customer);

    Customer dtoToEntity(@NonNull CustomerDTO customer);
//    @Mapping(target = "age", source = "customer.age")
    CustomerDTO requestToDTO(CustomerRequest customer);

    CustomerDTO entityToDTO(Customer customer);

    CustomerResponse dtoToResponse(CustomerDTO customerDTO);


}
