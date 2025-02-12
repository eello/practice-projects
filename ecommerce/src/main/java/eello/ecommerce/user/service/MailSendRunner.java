package eello.ecommerce.user.service;

import eello.ecommerce.user.entity.VerificationCode;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@RequiredArgsConstructor
public class MailSendRunner implements Runnable {

    private final JavaMailSender mailSender;
    private final MimeMessage message;
    private final VerificationCode vc;

    @Override
    public void run() {
        mailSender.send(message);
        log.info("send verification code: {} to {}", vc.getCode(), vc.getId());
    }
}
