package com.example.backend.validation.validator;


import com.example.backend.validation.annotation.EnumConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumConstraintValidator implements ConstraintValidator<EnumConstraint, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(EnumConstraint annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean isValid = acceptedValues.contains(value.toString());

        if (!isValid) {
            String enumValues = String.join(", ", acceptedValues);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must be any of: " + enumValues)
                    .addConstraintViolation();
        }
        return isValid;
    }
}
