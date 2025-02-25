package eello.ecommerce.user.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Phone {

    @Column(nullable = false)
    private String phone;

    public Phone(String phone) {
        this.phone = phone.replace("-", "");
    }
}
