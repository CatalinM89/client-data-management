package com.example.demo.mappers;

import com.example.demo.models.CustomerAddressDTO;
import com.example.demo.models.entities.Address;
import com.example.demo.models.CustomerAddress;
import org.mapstruct.Mapper;

/**
 * Mapping component to facilitate model conversions from request model to transfer object or from a transfer object to
 * the Address entity
 * @author Catalin Moisa
 */
@Mapper(componentModel = "spring")
public interface CustomerAddressMapper {
    CustomerAddressDTO requestToDTO(CustomerAddress customerAddress);

    Address dtoToEntity(CustomerAddressDTO customerAddressDTO);


}
