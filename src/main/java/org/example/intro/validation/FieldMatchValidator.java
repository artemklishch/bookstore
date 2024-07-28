//package org.example.intro.validation;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.apache.commons.beanutils.BeanUtils;
//
//public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
//    private String firstFieldName;
//    private String secondFieldName;
//
//    @Override
//    public void initialize(final FieldMatch constraintAnnotation)
//    {
//        firstFieldName = constraintAnnotation.first();
//        secondFieldName = constraintAnnotation.second();
//    }
//
//    @Override
//    public boolean isValid(final Object value, final ConstraintValidatorContext context)
//    {
//        try
//        {
//            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
//            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
//
//            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
//        }
//        catch (final Exception ignore)
//        {
//            // ignore
//        }
//        return true;
//    }
//}


package org.example.intro.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.mvel2.MVEL;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String field;
    private String verifyField;

    public void initialize(FieldMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.verifyField = constraintAnnotation.verifyField();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldObj = MVEL.getProperty(field, value);
        Object verifyFieldObj = MVEL.getProperty(verifyField, value);

        boolean neitherSet = (fieldObj == null) && (verifyFieldObj == null);

        if (neitherSet) {
            return true;
        }

        boolean matches = (fieldObj != null) && fieldObj.equals(verifyFieldObj);

        if (!matches) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Fields '{field}' and '{verifyField}' must match"
                    )
                    .addNode(verifyField)
                    .addConstraintViolation();
        }

        return matches;
    }
}
