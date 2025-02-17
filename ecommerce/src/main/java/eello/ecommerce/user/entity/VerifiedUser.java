package eello.ecommerce.user.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "VU", timeToLive = 3600)
@Getter
public class VerifiedUser {

    @Id
    private String id;

    public VerifiedUser(String id) {
        this.id = id;
    }
}
