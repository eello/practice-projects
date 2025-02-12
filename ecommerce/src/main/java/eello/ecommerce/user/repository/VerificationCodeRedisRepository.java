package eello.ecommerce.user.repository;

import eello.ecommerce.user.entity.VerificationCode;
import org.springframework.data.repository.CrudRepository;

public interface VerificationCodeRedisRepository extends CrudRepository<VerificationCode, String> {
}
