package eello.ecommerce.user.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketingConsent {

    private boolean email;
    private boolean snsOrSms;
    private boolean appPush;

    public MarketingConsent(boolean email, boolean snsOrSms, boolean appPush) {
        this.email = email;
        this.snsOrSms = snsOrSms;
        this.appPush = appPush;
    }
}
