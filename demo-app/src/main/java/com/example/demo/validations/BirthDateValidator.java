package com.example.demo.validations;

import com.example.demo.service.Clock;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Custom validator for the birthdate field, making sure the customer is an adult
 *
 * @author Catalin Moisa
 */
@Component
public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, String> {

    private final Clock clock;

    private final Integer minimumCustomerAge;

    private final DateTimeFormatter dateTimeFormatter;

    BirthDateValidator(@NonNull Clock clock,
                       @NonNull @Value("${restrictions.customer.min.age}") Integer minimumCustomerAge,
                       @NonNull @Value("${restrictions.customer.birthdate.format}") String birthdateFormat) {
        this.clock = clock;
        this.minimumCustomerAge = minimumCustomerAge;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(birthdateFormat);
    }

    @Override
    public void initialize(ValidBirthDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * @param birthdate                  the date of birth expressed in the ${restrictions.customer.birthdate.format}
     *                                   ISO 8601 format
     * @param constraintValidatorContext the validator context
     * @return the delta time, in years, between the current date and the customer provided birthdate
     */
    @Override
    public boolean isValid(String birthdate, ConstraintValidatorContext constraintValidatorContext) {
        return Period.between(LocalDate.parse(birthdate, dateTimeFormatter), clock.now()).getYears() >= minimumCustomerAge;
    }
}
