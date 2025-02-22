package eello.ecommerce.global.config;

import eello.ecommerce.global.config.argon2.Argon2Properties;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class Argon2ConfigTest {

    @Autowired
    private Argon2Properties properties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void propertiesInjectionTest() {
        assertThat(properties.getSaltLength()).isEqualTo(16);
        assertThat(properties.getHashLength()).isEqualTo(32);
        assertThat(properties.getMemory()).isEqualTo(12800);
        assertThat(properties.getParallelism()).isEqualTo(1);
        assertThat(properties.getIterations()).isEqualTo(1);
    }

    @Test
    public void hashTest() {
        String passwordHash = passwordEncoder.encode("a!b252123");
        assertThat(passwordEncoder.matches("a!b252123", passwordHash)).isTrue();
    }
}