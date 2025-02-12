package eello.ecommerce.user.service;

import eello.ecommerce.user.dto.request.VerificationCodeVerifyMailReqDTO;
import jakarta.mail.MessagingException;

public interface IdentityVerifier {

    void sendVerificationCodeTo(String target) throws MessagingException;
    boolean verify(VerificationCodeVerifyMailReqDTO dto);
}
