package eello.ecommerce.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TERMS_AGREEMENT")
public class TermsAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean over14; // 만 14세 이상
    private boolean termsOfService; // 서비스 이용 동의
    private boolean privacyPolicy; // 개인정보 수집 및 동의
    private boolean electronicFinanceTerms; // 마케팅 목적의 개인정보 수집 및 이용

    @Embedded
    private MarketingConsent marketingConsent;
}
