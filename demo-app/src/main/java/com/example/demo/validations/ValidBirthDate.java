package com.example.demo.validations;

import com.example.demo.models.CustomerRequest;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to be used to validate that the customer provided birthdate conforms with the required restrictions
 *
 * @author Catalin Moisa
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
@Documented
public @interface ValidBirthDate {

    String message() default "provided birthdate is not valid";

    Class<?>[] groups() default {};

    Class<? extends CustomerRequest>[] payload() default {};

}