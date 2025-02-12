package eello.ecommerce.user.controller;

import eello.ecommerce.user.dto.request.VerificationCodeMailReqDTO;
import eello.ecommerce.user.dto.request.VerificationCodeVerifyMailReqDTO;
import eello.ecommerce.user.service.IdentityVerifier;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IdentityVerifier iv;

    @PostMapping("/ivc")
    public void getIVC(@Valid @RequestBody VerificationCodeMailReqDTO vcReq) throws MessagingException {
        iv.sendVerificationCodeTo(vcReq.getReceiveAddress());
    }

    @PostMapping("/ivc/verification")
    public ResponseEntity<?> verifyIVC(@Valid @RequestBody VerificationCodeVerifyMailReqDTO dto) {
        boolean result = iv.verify(dto);
        if (result) {
            return ResponseEntity.ok().build();
        }

        Map<String, String> body = new HashMap<>();
        body.put("message", "인증번호가 만료되거나 일치하지 않습니다.");
        return ResponseEntity.badRequest().body(body);
    }
}
