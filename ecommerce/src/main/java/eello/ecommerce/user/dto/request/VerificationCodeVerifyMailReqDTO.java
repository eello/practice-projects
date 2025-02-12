package eello.ecommerce.user.dto.request;

import eello.ecommerce.validation.VerificationCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeVerifyMailReqDTO {

    @Email(message = "이메일 형식이어야 합니다.")
    @NotNull(message = "필수 입력 항목입니다.")
    private String receiveAddress;

    @VerificationCode
    @NotNull(message = "필수 입력 항목입니다.")
    private String ivc;
}
