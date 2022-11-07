package com.example.sboot.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = NoHtmlValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface NoHtml {

    // Should default to an error message key made of the fully-qualified class name
    // of the constraint followed by .message.
    String message() default "{com.example.sboot.utils.validation.NoHtml.message}";

    // For user to customize the targeted groups.
    Class<?>[] groups() default {};

    // For extensibility purposes.
    Class<? extends Payload>[] payload() default {};
}
