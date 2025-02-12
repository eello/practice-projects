package eello.ecommerce.validation;

import eello.ecommerce.validation.validator.VerificationCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {VerificationCodeValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerificationCode {

    String message() default "인증번호는 6자리 숫자입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
