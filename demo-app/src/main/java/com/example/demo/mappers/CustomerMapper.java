package com.example.demo.mappers;

import com.example.demo.models.CustomerDTO;
import com.example.demo.models.CustomerRequest;
import com.example.demo.models.CustomerResponse;
import com.example.demo.models.entities.Customer;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


/**
 * Mapping component to facilitate model conversions from request model to transfer object, from a transfer object to
 * the Customer entity, from entity to transfer object
 *
 * @author Catalin Moisa
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "id", ignore = true)
    Customer requestToEntity(CustomerRequest customer);

    Customer dtoToEntity(CustomerDTO customer);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "birthdate", expression = "java(CustomerMapper.birthdateToLocalDate(customer.getBirthdate()))")
    CustomerDTO requestToDTO(CustomerRequest customer);

    CustomerDTO entityToDTO(Customer customer);

    @Mapping(target = "age", expression = "java(CustomerMapper.birthdateToAge(customerDTO.getBirthdate()))")
    CustomerResponse dtoToResponse(CustomerDTO customerDTO);

    static LocalDate birthdateToLocalDate(@NonNull String birthdate) {
        return LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    static Integer birthdateToAge(@NonNull LocalDate birthdate) {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }


}
