package eello.ecommerce.user.entity;

import eello.ecommerce.global.BaseTimeEntity;
import eello.ecommerce.user.entity.embeddable.Email;
import eello.ecommerce.user.entity.embeddable.Password;
import eello.ecommerce.user.entity.embeddable.Phone;
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

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Phone phone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "terms_agreement_id")
    private TermsAgreement termsAgreement;

    @Builder
    public User(Long id, String name, Email email, Phone phone, Password password, TermsAgreement termsAgreement) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.termsAgreement = termsAgreement;
    }
}
