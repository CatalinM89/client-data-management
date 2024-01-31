package com.example.demo.mappers;

import com.example.demo.models.CustomerAddressDTO;
import com.example.demo.models.entities.Address;
import com.example.demo.models.CustomerAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerAddressMapper {
    CustomerAddressDTO requestToDTO(CustomerAddress customerAddress);

    Address dtoToEntity(CustomerAddressDTO customerAddressDTO);


}
