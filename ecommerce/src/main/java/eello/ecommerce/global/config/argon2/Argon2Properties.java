package eello.ecommerce.global.config.argon2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Argon2Properties {

    private int memory;
    private int iterations;
    private int parallelism;
    private int saltLength;
    private int hashLength;
}
