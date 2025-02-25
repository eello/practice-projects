package eello.ecommerce.user.repository;

import eello.ecommerce.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u.id) > 0 FROM User u WHERE u.email.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u.id) > 0 FROM User u WHERE u.phone.phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);

    @Query("SELECT u FROM User u WHERE u.phone.phone = :phone")
    Optional<User> findByPhone(@Param("phone") String phone);
    
}
