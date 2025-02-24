package eello.ecommerce.user.dto.request;

import eello.ecommerce.validation.Phone;

public class PhoneCheckReqDTO {

    @Phone
    private String phone;

    public String getPhone() {
        return phone.replace("-", "");
    }
}
