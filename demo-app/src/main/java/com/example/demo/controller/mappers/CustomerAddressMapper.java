package com.example.demo.controller.mappers;

import com.example.demo.controller.models.CustomerAddressDTO;
import com.example.demo.controller.models.entities.Address;
import com.example.demo.models.CustomerAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerAddressMapper {
    CustomerAddressDTO requestToDTO(CustomerAddress customerAddress);

    Address dtoToEntity(CustomerAddressDTO customerAddressDTO);


}
