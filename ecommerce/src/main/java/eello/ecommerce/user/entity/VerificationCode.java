package eello.ecommerce.user.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "VC", timeToLive = 180)
@Getter
@ToString
public class VerificationCode {

    @Id
    private String id;
    private String code;

    public VerificationCode(String id, String code) {
        this.id = id;
        this.code = code;
    }

    public boolean verify(String code) {
        return this.code.equals(code);
    }
}
