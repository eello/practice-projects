package eello.ecommerce.global.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, Object> template;

    @Test
    public void redisConnectionTest() {
        template.opsForValue().set("hihi", "hello");
    }
}