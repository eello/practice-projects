package eello.ecommerce.user.service;

import eello.ecommerce.user.dto.request.SignupReqDTO;

public interface UserService {

    Long register(SignupReqDTO dto);
}
