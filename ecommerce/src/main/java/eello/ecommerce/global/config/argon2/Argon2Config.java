package eello.ecommerce.global.config.argon2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Argon2Config {

    @Bean
    @ConfigurationProperties(prefix = "argon2")
    public Argon2Properties argon2Properties() {
        return new Argon2Properties();
    }

    @Bean
    public PasswordEncoder passwordEncoder(Argon2Properties properties) {
        return new Argon2PasswordEncoder(
                properties.getSaltLength(),
                properties.getHashLength(),
                properties.getParallelism(),
                properties.getMemory(),
                properties.getIterations()
        );
    }
}
