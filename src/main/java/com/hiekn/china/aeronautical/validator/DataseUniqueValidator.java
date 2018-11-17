package com.hiekn.china.aeronautical.validator;

import com.hiekn.china.aeronautical.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DataseUniqueValidator implements ConstraintValidator<DatasetUnique, String> {
    @Autowired
    private DatasetRepository datasetRepository;

    @Override
    public void initialize(DatasetUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        System.out.println(value);

        return false;
    }
}
