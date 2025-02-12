package eello.ecommerce.user.service;

import eello.ecommerce.user.entity.VerificationCode;
import eello.ecommerce.user.repository.VerificationCodeRedisRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultIdentityVerifier implements IdentityVerifier {

    private final VerificationCodeGenerator vcGenerator;
    private final VerificationCodeRedisRepository vcRepository;
    private final VerificationCodeSender vcSender;

    @Override
    public void sendVerificationCodeTo(String target) throws MessagingException {
        String code = vcGenerator.generate();

        // 인증코드 생성 및 저장
        VerificationCode vc = new VerificationCode(target, code);
        vcRepository.save(vc);

        // 인증코드 전송
        vcSender.send(vc);
    }
}
