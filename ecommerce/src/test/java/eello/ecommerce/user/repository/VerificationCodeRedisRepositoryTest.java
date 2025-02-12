package eello.ecommerce.user.repository;

import eello.ecommerce.user.entity.VerificationCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class VerificationCodeRedisRepositoryTest {

    @Autowired
    private VerificationCodeRedisRepository vcRepository;

    @Test
    public void save() {
        VerificationCode verificationCode = new VerificationCode("01012345678", "234567");
        vcRepository.save(verificationCode);

        Optional<VerificationCode> find = vcRepository.findById(verificationCode.getId());

        assertThat(find.isPresent()).isTrue();
        assertThat(find.get().getId()).isEqualTo(verificationCode.getId());
    }
}