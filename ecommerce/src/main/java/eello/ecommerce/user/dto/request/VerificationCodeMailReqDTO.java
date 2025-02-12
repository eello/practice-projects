package eello.ecommerce.user.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class VerificationCodeMailReqDTO {

    @Email(message = "이메일 형식이어야 합니다.")
    private String receiveAddress;
}
