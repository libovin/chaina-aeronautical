package com.hiekn.china.aeronautical.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DataseUniqueValidator implements ConstraintValidator<DatasetUnique, String> {

    @Override
    public void initialize(DatasetUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        System.out.println(value);

        return false;
    }
}
