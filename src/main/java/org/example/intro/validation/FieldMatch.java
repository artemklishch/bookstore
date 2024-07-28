package org.example.intro.validation;//package org.example.intro.validation;
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Constraint(validatedBy = FieldMatchValidator.class)
//@Target({ElementType.PARAMETER, ElementType.FIELD})
//@Retention(RetentionPolicy.RUNTIME)
//public @interface FieldMatch {
//    String message() default "{constraints.fieldmatch}";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
@Documented
public @interface FieldMatch {

    String message() default "Fields must match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    String verifyField();
}
