package eello.ecommerce.user.entity;

import eello.ecommerce.global.config.argon2.Argon2Properties;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Argon2Args {

    @Column(nullable = false)
    private int saltLength;

    @Column(nullable = false)
    private int hashLength;

    @Column(nullable = false)
    private int memoryCost;

    @Column(nullable = false)
    private int iterations;

    @Column(nullable = false)
    private int parallelism;

    public Argon2Args(Argon2Properties properties) {
        saltLength = properties.getSaltLength();
        hashLength = properties.getHashLength();
        memoryCost = properties.getMemory();
        iterations = properties.getIterations();
        parallelism = properties.getParallelism();
    }
}
