package eello.ecommerce.user.service;

import jakarta.mail.MessagingException;

public interface IdentityVerifier {

    void sendVerificationCodeTo(String target) throws MessagingException;
}
