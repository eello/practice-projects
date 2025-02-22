package eello.ecommerce.user.entity;

import eello.ecommerce.global.BaseTimeEntity;
import eello.ecommerce.global.config.argon2.Argon2Properties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "USERS",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_users_phone", columnNames = "phone")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String passwordHash;

    @Embedded
    private Argon2Args argon2Args;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "terms_agreement_id")
    private TermsAgreement termsAgreement;

    @Builder
    public User(Long id, String name, String email, String phone, String passwordHash, Argon2Properties argon2Properties, TermsAgreement termsAgreement) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.argon2Args = new Argon2Args(argon2Properties);
        this.termsAgreement = termsAgreement;
    }
}
