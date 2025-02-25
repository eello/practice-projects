package eello.ecommerce.user.service;

import eello.ecommerce.global.config.argon2.Argon2Properties;
import eello.ecommerce.global.exception.CustomException;
import eello.ecommerce.global.exception.ErrorCode;
import eello.ecommerce.user.dto.request.SignupReqDTO;
import eello.ecommerce.user.entity.embeddable.Email;
import eello.ecommerce.user.entity.embeddable.Password;
import eello.ecommerce.user.entity.embeddable.Phone;
import eello.ecommerce.user.entity.User;
import eello.ecommerce.user.repository.UserRepository;
import eello.ecommerce.user.repository.VerifiedUserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final VerifiedUserRedisRepository vuRepository;
    private final Argon2Properties argon2Properties;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long register(SignupReqDTO dto) {
        /**
         * 회원가입이 실패하는 경우
         * 1. 필수 동의 항목에 모두 동의하지 않은 경우
         * 2. 본인인증이 이루어지지 않은 이메일인 경우
         * 3. password != confirmPassword
         * 4. 이미 등록된 사용자: 이메일 혹은 휴대폰 번호로 가입한 사용자가 존재할 경우
         */

        if (!dto.checkRequiredTerms()) {
            // 필수 동의 항목에 모두 동의하지 않은 경우
            throw new CustomException(ErrorCode.USER_REQUIRED_TERMS_DISAGREE);
        }

        if (!dto.getPwd().equals(dto.getConfirmPwd())) {
            // password != confirmPassword
            throw new CustomException(ErrorCode.USER_PASSWORD_MISMATCH);
        }

        if (!vuRepository.existsById(dto.getEmail())) {
            // 본인인증이 이루어지지 않은 이메일인 경우
            // 본인인증 방식이 휴대폰 번호로 바뀌면 vuRepository.existsById(dto.getPhone())으로 수정해야함.
            throw new CustomException(ErrorCode.USER_NO_IDENTITY_VERIFICATION);
        }

        if (userRepository.existsByEmail(dto.getEmail()) || userRepository.existsByPhone(dto.getPhone())) {
            // 이미 등록된 사용자: 이메일 혹은 휴대폰 번호로 가입한 사용자가 존재할 경우
            throw new CustomException(ErrorCode.USER_ALREADY_REGISTERED);
        }

        User user = User.builder()
                .name(dto.getName())
                .email(new Email(dto.getEmail()))
                .phone(new Phone(dto.getPhone()))
                .password(new Password(dto.getPhone(), passwordEncoder))
                .termsAgreement(dto.getTermsAgreement())
                .build();

        user = userRepository.save(user);

        return user.getId();
    }
}
