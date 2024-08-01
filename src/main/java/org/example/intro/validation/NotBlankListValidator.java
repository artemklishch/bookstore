package org.example.intro.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class NotBlankListValidator implements ConstraintValidator<NotBlankList, List<String>> {
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        for (String str : value) {
            if (str == null || str.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
