package eello.ecommerce.user.dto.request;

import eello.ecommerce.validation.Password;
import eello.ecommerce.validation.Phone;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SignupReqDTO {

    @Email
    private String email;

    @Password
    private String pwd;

    @Password
    private String confirmPwd;

    private String name;

    @Phone
    private String phone;

    private TermsAgreement agree;

    public String getPhone() {
        return phone.replace("-", "");
    }

    @Data
    public static class TermsAgreement {
        private boolean over14;
        private boolean termsOfService;
        private boolean privacyPolicy;
        private boolean electronicFinanceTerms;
        private MarketingConsent marketingConsent;
    }

    @Data
    public static class MarketingConsent {
        private boolean email;
        private boolean snsOrSms;
        private boolean appPush;
    }

    public boolean checkRequiredTerms() {
        return agree.over14 && agree.termsOfService && agree.privacyPolicy && agree.electronicFinanceTerms;
    }

    public eello.ecommerce.user.entity.TermsAgreement getTermsAgreement() {
        return eello.ecommerce.user.entity.TermsAgreement.builder()
                .email(agree.marketingConsent.email)
                .snsOrSms(agree.marketingConsent.snsOrSms)
                .appPush(agree.marketingConsent.appPush)
                .build();
    }
}
