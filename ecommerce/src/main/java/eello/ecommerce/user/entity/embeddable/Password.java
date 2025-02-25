package eello.ecommerce.user.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Password {

    @Column(nullable = false)
    private String passwordHash;

    @Transient
    private String plainPassword;

    public Password(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public Password(String plainPassword, PasswordEncoder passwordEncoder) {
        this.plainPassword = plainPassword;
        encode(passwordEncoder);
    }

    public void encode(PasswordEncoder encoder) {
        if (this.passwordHash == null) {
            this.passwordHash = encoder.encode(this.plainPassword);
        }
    }
}
