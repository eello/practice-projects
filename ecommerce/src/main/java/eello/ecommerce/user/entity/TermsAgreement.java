package eello.ecommerce.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TERMS_AGREEMENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TermsAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean over14 = true; // 만 14세 이상
    private boolean termsOfService = true; // 서비스 이용 동의
    private boolean privacyPolicy = true; // 개인정보 수집 및 동의
    private boolean electronicFinanceTerms = true; // 전자금융거래 이용약관

    @Embedded
    private MarketingConsent marketingConsent = new MarketingConsent(); // 마케팅 목적의 개인정보 수집 및 이용

    @Builder
    public TermsAgreement(Long id, boolean email, boolean snsOrSms, boolean appPush) {
        this.id = id;
        this.marketingConsent.email = email;
        this.marketingConsent.snsOrSms = snsOrSms;
        this.marketingConsent.appPush = appPush;
    }

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class MarketingConsent {

        private boolean email;
        private boolean snsOrSms;
        private boolean appPush;

        public MarketingConsent(boolean email, boolean snsOrSms, boolean appPush) {
            this.email = email;
            this.snsOrSms = snsOrSms;
            this.appPush = appPush;
        }
    }
}
