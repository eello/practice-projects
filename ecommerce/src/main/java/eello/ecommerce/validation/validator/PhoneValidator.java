package eello.ecommerce.validation.validator;

import eello.ecommerce.validation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    public static final String PHONE_NUMBER_REGEX = "^01[0-9]-\\d{4}-\\d{4}$|^01[0-9]\\d{8}$";
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return PHONE_NUMBER_PATTERN.matcher(value).matches();
    }
}
