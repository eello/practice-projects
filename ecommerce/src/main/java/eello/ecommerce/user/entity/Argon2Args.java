package eello.ecommerce.user.entity;

import eello.ecommerce.global.config.Argon2Config;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Argon2Args {

    private int memoryCost;
    private int iterations;
    private int parallelism;

    public Argon2Args(Argon2Config argon2Config) {
        this(argon2Config.getMemory(), argon2Config.getIterations(), argon2Config.getParallelism());
    }

    public Argon2Args(int memoryCost, int iterations, int parallelism) {
        this.memoryCost = memoryCost;
        this.iterations = iterations;
        this.parallelism = parallelism;
    }
}
