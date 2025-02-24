package eello.ecommerce.user.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class EmailCheckReqDTO {

    @Email
    private String email;
}
