package fr.dta.constraintes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = ISBNValidator.class)
@java.lang.annotation.Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBN {
	String message() default "{validator.ISBN}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
