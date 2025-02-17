package eello.ecommerce.user.repository;

import eello.ecommerce.user.entity.VerifiedUser;
import org.springframework.data.repository.CrudRepository;

public interface VerifiedUserRedisRepository extends CrudRepository<VerifiedUser, String> {
}
