package eello.ecommerce.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class DuplicationCheckResDTO {

    private boolean available;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public DuplicationCheckResDTO(boolean available) {
        this(available, null);
    }

    public DuplicationCheckResDTO(boolean available, String message) {
        this.available = available;
        this.message = message;
    }
}
