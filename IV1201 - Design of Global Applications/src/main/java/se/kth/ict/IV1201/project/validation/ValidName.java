package se.kth.ict.IV1201.project.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * The annotated target is checked to be a name(surname and given name).
 * A valid name is longer than 1 character and less than 30 character and
 * does not contain numbers.
 * 
 * @author Konstantin Sozinov
 */
@Constraint(validatedBy = ValidName.NameValidator.class)
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {

    String message() default "{invalidName}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class NameValidator implements ConstraintValidator<ValidName, String> {

        @Override   
        public void initialize(ValidName constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (isEmpty(value, context)) {
                return false;
            }
            if(value.length() <= 1 || value.length() > 30 || value.matches(".*\\d+.*")) return false;
            return true;
        }

        private boolean isEmpty(String value, ConstraintValidatorContext context) {
            if (value.length() == 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("{emptyName}").addConstraintViolation();
                return true;
            }
            return false;
        }
    }
}
