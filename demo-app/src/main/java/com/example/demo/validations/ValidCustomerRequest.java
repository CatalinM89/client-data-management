package com.example.demo.validations;

import com.example.demo.models.CustomerRequest;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to be used to validate that the customer provided either the email or all of the address fields
 *
 * @author Catalin Moisa
 */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CustomerRequestValidator.class)
@Documented
public @interface ValidCustomerRequest {

    String message() default "Request is not valid. Provide a valid email or all of the address fields";

    Class<?>[] groups() default {};

    Class<? extends CustomerRequest>[] payload() default {};

}