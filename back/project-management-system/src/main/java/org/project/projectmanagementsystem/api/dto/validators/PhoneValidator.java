package org.project.projectmanagementsystem.api.dto.validators;

import org.project.projectmanagementsystem.api.annotations.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<ValidPhoneNumber,String> {

    private static final String PHONE_NUMBER = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$";
    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER);
        Matcher matcher = pattern.matcher(phone);

        return matcher.matches();
    }
}
