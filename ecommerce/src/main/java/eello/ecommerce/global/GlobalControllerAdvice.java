package eello.ecommerce.global;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldErrorDetail> invalidFields = ex.getBindingResult().getFieldErrors().stream()
                .map((fieldError -> new FieldErrorDetail(fieldError.getField(), fieldError.getDefaultMessage())))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "올바르지 않은 형식 포함");
        response.put("invalidFields", invalidFields);

        return ResponseEntity.badRequest().body(response);
    }

    @Getter
    private static class FieldErrorDetail {
        private String field;
        private String desc;

        public FieldErrorDetail(String field, String desc) {
            this.field = field;
            this.desc = desc;
        }
    }
}
