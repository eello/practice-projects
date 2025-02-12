package eello.ecommerce.validation.validator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationCodeValidatorTest {

    private VerificationCodeValidator validator = new VerificationCodeValidator();

    @Test
    public void success() {
        String vc = "000000";
        assertThat(VerificationCodeValidator.VERIFICATION_CODE_PATTERN.matcher(vc).matches())
                .isTrue();
    }

    @Test
    public void failure() {
        String vc = "0000a0";
        assertThat(VerificationCodeValidator.VERIFICATION_CODE_PATTERN.matcher(vc).matches())
                .isFalse();
    }
}