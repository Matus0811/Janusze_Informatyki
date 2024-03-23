package org.project.projectmanagementsystem.controller.annotations;

import org.project.projectmanagementsystem.controller.validators.PhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
@Documented
public @interface ValidPhoneNumber {
    String message() default "Invalid phone";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
