package com.ngymkana.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.ngymkana.validation.StateValidation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented // Meta annotation
@Target({ FIELD}) // Meta annotation
@Retention(RUNTIME) // Meta annotation
@Constraint(validatedBy = { StateValidation.class}) // Validation class
public @interface State {
    // Error message
    String message() default "State value must be 'published' or 'draft'";
    // Groups
    Class<?>[] groups() default { };
    // Payload
    Class<? extends Payload>[] payload() default { };
}
