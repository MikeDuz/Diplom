package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import skillbox.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int userId);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.code = ?1 where u.id = ?2")
    void setCode(String code, int userId);


}
