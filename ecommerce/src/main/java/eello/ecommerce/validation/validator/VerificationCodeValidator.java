package eello.ecommerce.validation.validator;

import eello.ecommerce.validation.VerificationCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class VerificationCodeValidator implements ConstraintValidator<VerificationCode, String> {

    public static final Pattern VERIFICATION_CODE_PATTERN = Pattern.compile("^\\d{6}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return VERIFICATION_CODE_PATTERN.matcher(value).matches();
    }
}
