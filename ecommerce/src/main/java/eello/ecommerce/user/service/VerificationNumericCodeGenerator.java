package eello.ecommerce.user.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class VerificationNumericCodeGenerator implements VerificationCodeGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generate() {
        int code = 100_000 + RANDOM.nextInt(900_000); // 100000 ~ 999999 범위
        return String.valueOf(code);
    }
}
