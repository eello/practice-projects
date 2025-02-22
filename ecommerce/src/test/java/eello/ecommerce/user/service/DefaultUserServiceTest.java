package eello.ecommerce.user.service;

import eello.ecommerce.global.config.argon2.Argon2Properties;
import eello.ecommerce.global.exception.CustomException;
import eello.ecommerce.global.exception.ErrorCode;
import eello.ecommerce.user.dto.request.SignupReqDTO;
import eello.ecommerce.user.entity.User;
import eello.ecommerce.user.repository.UserRepository;
import eello.ecommerce.user.repository.VerifiedUserRedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VerifiedUserRedisRepository vuRepository;

    @Spy
    private Argon2Properties argon2Properties;

    @Spy
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DefaultUserService userService;

    private SignupReqDTO dto;

    @BeforeEach
    public void setUp() {
        argon2Properties = new Argon2Properties();
        argon2Properties.setSaltLength(16);
        argon2Properties.setHashLength(32);
        argon2Properties.setMemory(25600);
        argon2Properties.setIterations(5);
        argon2Properties.setParallelism(2);

        passwordEncoder = new Argon2PasswordEncoder(
                argon2Properties.getSaltLength(),
                argon2Properties.getHashLength(),
                argon2Properties.getParallelism(),
                argon2Properties.getMemory(),
                argon2Properties.getIterations()
        );

        SignupReqDTO.MarketingConsent marketingConsent = new SignupReqDTO.MarketingConsent();
        marketingConsent.setEmail(false);
        marketingConsent.setSnsOrSms(false);
        marketingConsent.setAppPush(false);

        SignupReqDTO.TermsAgreement agree = new SignupReqDTO.TermsAgreement();
        agree.setOver14(true);
        agree.setTermsOfService(true);
        agree.setPrivacyPolicy(true);
        agree.setElectronicFinanceTerms(true);
        agree.setMarketingConsent(marketingConsent);

        dto = new SignupReqDTO();
        dto.setEmail("email@example.com");
        dto.setPwd("a!BS50df21%^@");
        dto.setConfirmPwd("a!BS50df21%^@");
        dto.setName("username");
        dto.setPhone("01012345678");
        dto.setAgree(agree);
    }

    @Test
    public void register_REQUIRED_TERMS_DISAGREE() {
        // given
        dto.getAgree().setElectronicFinanceTerms(false); // (필수)전자금융거래 이용약관 거부

        // then
        assertThrownExceptionHasErrorCode(ErrorCode.USER_REQUIRED_TERMS_DISAGREE);
    }

    @Test
    public void register_PASSWORD_MISMATCH() {
        // given
        dto.setConfirmPwd("b!sdD50fd2^@"); // pwd = a!BS50df21%^@

        // then
        assertThrownExceptionHasErrorCode(ErrorCode.USER_PASSWORD_MISMATCH);
    }

    @Test
    public void register_NO_IDENTITY_VERIFICATION() {
        // given
        when(vuRepository.existsById(anyString())).thenReturn(false);

        // then
        assertThrownExceptionHasErrorCode(ErrorCode.USER_NO_IDENTITY_VERIFICATION);
    }

    @Test
    public void register_ALREADY_REGISTERED() {
        // given
        when(vuRepository.existsById(anyString())).thenReturn(true);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // then
        assertThrownExceptionHasErrorCode(ErrorCode.USER_ALREADY_REGISTERED);
    }

    @Test
    public void register_SUCCESS() {
        // given
        User user = User.builder()
                .id(1L)
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .passwordHash(passwordEncoder.encode(dto.getPwd()))
                .argon2Properties(argon2Properties)
                .termsAgreement(dto.getTermsAgreement())
                .build();

        when(vuRepository.existsById(anyString())).thenReturn(true);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        Long userId = userService.register(dto);

        // then
        assertThat(userId).isEqualTo(user.getId());
        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).existsByPhone(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    private void assertThrownExceptionHasErrorCode(ErrorCode errorCode) {
        assertThatThrownBy(() -> userService.register(dto))
                .isInstanceOf(CustomException.class)
                .hasMessage(errorCode.getDescription());
    }
}