package com.example.demo.controller.mappers;

import com.example.demo.controller.models.CustomerAddressDTO;
import com.example.demo.controller.models.entities.Address;
import com.example.demo.models.CustomerAddress;
import lombok.NonNull;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerAddressMapper {
    CustomerAddressDTO requestToDTO(@NonNull CustomerAddress customerAddress);

    Address dtoToEntity(@NonNull CustomerAddressDTO customerAddressDTO);


}
