package com.example.libraryreservation.common.validation.enumValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {
    private EnumValid annotation;

    @Override
    public void initialize(EnumValid annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        for (Object enumValue : enumValues) {
            if (value.equals(enumValue.toString())
                    || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                return true;
            }
        }
        return false;
    }
}
