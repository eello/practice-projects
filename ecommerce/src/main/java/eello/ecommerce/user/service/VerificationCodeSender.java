package eello.ecommerce.user.service;

import eello.ecommerce.user.entity.VerificationCode;
import jakarta.mail.MessagingException;

public interface VerificationCodeSender {

    void send(VerificationCode verificationCode) throws MessagingException;
}
