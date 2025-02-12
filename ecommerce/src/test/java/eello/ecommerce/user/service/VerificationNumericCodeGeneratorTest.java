package eello.ecommerce.user.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationNumericCodeGeneratorTest {

    VerificationCodeGenerator vcg = new VerificationNumericCodeGenerator();

    @Test
    public void generateCode() {
        String vc = vcg.generate();
        assertThat(vc).matches("\\d+");
        assertThat(vc.length()).isEqualTo(6);
    }

}