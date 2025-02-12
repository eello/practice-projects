package eello.ecommerce.user.controller;

import eello.ecommerce.user.dto.request.VerificationCodeMailReqDTO;
import eello.ecommerce.user.service.IdentityVerifier;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IdentityVerifier iv;

    @PostMapping("/ivc")
    public void getIVC(@Valid @RequestBody VerificationCodeMailReqDTO vcReq) throws MessagingException {
        iv.sendVerificationCodeTo(vcReq.getReceiveAddress());
    }
}
