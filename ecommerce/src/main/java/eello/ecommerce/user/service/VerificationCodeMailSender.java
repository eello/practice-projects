package eello.ecommerce.user.service;

import eello.ecommerce.user.entity.VerificationCode;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
public class VerificationCodeMailSender implements VerificationCodeSender {

    private final JavaMailSender javaMailSender;

    @Qualifier("MailingVirtualThreadExecutor")
    private final Executor mailSendExecutor;

    @Override
    public void send(VerificationCode vc) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject("본인인증 번호 발송");
        message.setRecipients(Message.RecipientType.TO, vc.getId());
        message.setContent(buildHtmlText(vc.getCode()), "text/html; charset=utf-8");

        mailSendExecutor.execute(new MailSendRunner(javaMailSender, message, vc));
    }

    private String buildHtmlText(String code) {
        return "<h1>본인인증 번호</h1>" +
                "<br>" +
                "<p>본인인증번호 <strong>[ " + code + " ]</strong> 을 입력해주세요.";
    }
}
