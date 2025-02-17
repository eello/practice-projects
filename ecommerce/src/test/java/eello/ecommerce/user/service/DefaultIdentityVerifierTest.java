package eello.ecommerce.user.service;

import eello.ecommerce.user.dto.request.VerificationCodeVerifyMailReqDTO;
import eello.ecommerce.user.entity.VerificationCode;
import eello.ecommerce.user.entity.VerifiedUser;
import eello.ecommerce.user.repository.VerificationCodeRedisRepository;
import eello.ecommerce.user.repository.VerifiedUserRedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultIdentityVerifierTest {

    @Mock
    private VerificationCodeGenerator vcGenerator;

    @Mock
    private VerificationCodeRedisRepository vcRepository;

    @Mock
    private VerificationCodeSender vcSender;

    @Mock
    private VerifiedUserRedisRepository vuRepository;

    @InjectMocks
    private DefaultIdentityVerifier verifier;

    @Test
    public void verify_notFoundReceiveAddress() {
        String receiveAddress = "receiveAddress";
        String code = "123456";
        VerificationCodeVerifyMailReqDTO dto = new VerificationCodeVerifyMailReqDTO(receiveAddress, code);

        when(vcRepository.findById(any(String.class))).thenReturn(Optional.empty());

        //when
        boolean result = verifier.verify(dto);

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void verify_codeNotMatch() {
        //given
        String receiveAddress = "receiveAddress";
        String code = "123456";
        VerificationCodeVerifyMailReqDTO dto = new VerificationCodeVerifyMailReqDTO(receiveAddress, code);
        VerificationCode vc = new VerificationCode(receiveAddress, "234567");

        when(vcRepository.findById(any(String.class))).thenReturn(Optional.empty());

        //when
        boolean result = verifier.verify(dto);

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void verify_success() {
        //given
        String receiveAddress = "receiveAddress";
        String code = "123456";
        VerificationCodeVerifyMailReqDTO dto = new VerificationCodeVerifyMailReqDTO(receiveAddress, code);
        VerificationCode vc = new VerificationCode(receiveAddress, code);

        when(vcRepository.findById(any(String.class))).thenReturn(Optional.of(vc));

        //when
        boolean result = verifier.verify(dto);

        //then
        assertThat(result).isTrue();
        verify(vuRepository, times(1)).save(any(VerifiedUser.class));
    }
}