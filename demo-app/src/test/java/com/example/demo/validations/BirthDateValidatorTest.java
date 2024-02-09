package com.example.demo.validations;

import com.example.demo.service.Clock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BirthDateValidatorTest {

    @Mock
    private Clock clock;

    private final Integer minimumCustomerAge = 18;

    private final String dateTimeFormat = "dd-MM-yyyy";

    private final LocalDate EXPECTED_DATE = LocalDate.of(2023,2,9);
    private BirthDateValidator birthDateValidator;

    @BeforeEach
    public void setup() {
        when(clock.now()).thenReturn(EXPECTED_DATE);
        birthDateValidator = new BirthDateValidator(clock, minimumCustomerAge, dateTimeFormat);

    }

    @Test
    public void isValidBirthdate() {
        assertTrue(birthDateValidator.isValid("09-12-1989", null));
    }

    @Test
    public void isInValidBirthdate() {
        assertFalse(birthDateValidator.isValid("32-12-1989", null));
    }


}