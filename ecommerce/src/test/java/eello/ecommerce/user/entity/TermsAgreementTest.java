package eello.ecommerce.user.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TermsAgreementTest {

    @Test
    public void builder() {
        TermsAgreement terms = TermsAgreement.builder()
                .id(1L)
                .email(false)
                .snsOrSms(false)
                .appPush(true)
                .build();

        System.out.println(terms);
    }

}