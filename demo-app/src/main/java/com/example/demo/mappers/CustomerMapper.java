package com.example.demo.mappers;

import com.example.demo.models.CustomerDTO;
import com.example.demo.models.entities.Customer;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapping component to facilitate model conversions from request model to transfer object, from a transfer object to
 * the Customer entity, from entity to transfer object
 * @author Catalin Moisa
 */
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
