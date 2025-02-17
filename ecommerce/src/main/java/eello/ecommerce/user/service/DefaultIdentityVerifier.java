package eello.ecommerce.user.service;

import eello.ecommerce.user.dto.request.VerificationCodeVerifyMailReqDTO;
import eello.ecommerce.user.entity.VerificationCode;
import eello.ecommerce.user.entity.VerifiedUser;
import eello.ecommerce.user.repository.VerificationCodeRedisRepository;
import eello.ecommerce.user.repository.VerifiedUserRedisRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultIdentityVerifier implements IdentityVerifier {

    private final VerificationCodeGenerator vcGenerator;
    private final VerificationCodeRedisRepository vcRepository;
    private final VerificationCodeSender vcSender;
    private final VerifiedUserRedisRepository vuRepository;

    @Override
    public void sendVerificationCodeTo(String target) throws MessagingException {
        String code = vcGenerator.generate();

        // 인증코드 생성 및 저장
        VerificationCode vc = new VerificationCode(target, code);
        vcRepository.save(vc);

        // 인증코드 전송
        vcSender.send(vc);
    }

    @Override
    public boolean verify(VerificationCodeVerifyMailReqDTO dto) {
        Boolean result = vcRepository.findById(dto.getReceiveAddress())
                .map(verificationCode -> verificationCode.verify(dto.getIvc()))
                .orElse(false);

        if (!result) {
            return false;
        }

        // 인증된 유저 저장
        vuRepository.save(new VerifiedUser(dto.getReceiveAddress()));
        return true;
    }
}
