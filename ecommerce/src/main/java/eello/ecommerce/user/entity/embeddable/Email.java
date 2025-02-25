package eello.ecommerce.user.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Email {

    @Column(nullable = false)
    private String email;

    @Transient
    private String id;

    @Transient
    private String domain;

    public Email(String email) {
        this.email = email;
    }

    public String getId() {
        if (id == null) {
            disassemble();
        }

        return id;
    }

    public String getDomain() {
        if (domain == null) {
            disassemble();
        }

        return domain;
    }

    public String getMaskedEmail() {
        return getId().substring(0, 2) + "*".repeat(getId().length() - 2)
                + "@"
                + getDomain();
    }

    private void disassemble() {
        String[] split = email.split("@");
        this.id = split[0];
        this.domain = split[1];
    }
}
