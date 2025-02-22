package eello.ecommerce.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /**
     * 네이밍 : DOMAIN_ + 에러 원인
     * HttpStatus : 예외로 인한 실패 응답시 사용될 HttpStatus, 기본값 = BAD_REQUEST
     */

    USER_PASSWORD_MISMATCH("비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    USER_REQUIRED_TERMS_DISAGREE("필수 동의 항목에 동의하지 않았습니다."),
    USER_NO_IDENTITY_VERIFICATION("본인인증이 되지 않았거나 본인인증 기간이 만료되었습니다."),
    USER_ALREADY_REGISTERED("이미 등록된 사용자 입니다.", CONFLICT),
    ;

    private final String description;
    private final HttpStatus httpStatus;

    ErrorCode(String description) {
        this(description, BAD_REQUEST);
    }

    ErrorCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }

}
