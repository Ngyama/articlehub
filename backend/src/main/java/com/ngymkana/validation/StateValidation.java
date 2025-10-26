package com.ngymkana.validation;

import com.ngymkana.anno.State;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {
    /**
     *
     * @param value data to validate
     * @param context context in which the constraint is evaluated
     *
     * @return true if valid, false otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Validation rules
        if (value == null){
            return false;
        }
        if (value.equals("published") || value.equals("draft")){
            return true;
        }
        return false;
    }
}
