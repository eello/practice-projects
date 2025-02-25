package eello.ecommerce.user.controller;

import eello.ecommerce.user.dto.request.*;
import eello.ecommerce.user.dto.response.DuplicationCheckResDTO;
import eello.ecommerce.user.repository.UserRepository;
import eello.ecommerce.user.service.IdentityVerifier;
import eello.ecommerce.user.service.UserService;
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
    private final UserService userService;
    private final UserRepository userRepository;

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

    @PostMapping
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDTO dto) {
        userService.register(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check-email")
    public ResponseEntity<DuplicationCheckResDTO> checkEmail(@Valid @RequestBody EmailCheckReqDTO dto) {
        DuplicationCheckResDTO response;
        if (userRepository.existsByEmail(dto.getEmail())) {
            response = new DuplicationCheckResDTO(false, "이미 가입된 이메일 주소입니다.");
        } else {
            response = new DuplicationCheckResDTO(true);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-phone")
    public ResponseEntity<DuplicationCheckResDTO> checkPhone(@Valid @RequestBody PhoneCheckReqDTO dto) {
        DuplicationCheckResDTO response = userRepository.findByPhone(dto.getPhone())
                .map(user -> new DuplicationCheckResDTO(
                        false,
                        user.getEmail().getMaskedEmail() + " 이메일로 가입된 휴대폰 번호입니다.")
                ).orElseGet(() -> new DuplicationCheckResDTO(true));

        return ResponseEntity.ok(response);
    }
}
