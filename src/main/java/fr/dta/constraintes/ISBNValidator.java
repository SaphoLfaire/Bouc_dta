package fr.dta.constraintes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import fr.dta.constraintes.ISBN;

import org.springframework.util.StringUtils;

public class ISBNValidator implements ConstraintValidator<ISBN, String> {
	public void initialize(ISBN constraintAnnotation) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isEmpty(value) || (value.length() == 10);
	}//
}