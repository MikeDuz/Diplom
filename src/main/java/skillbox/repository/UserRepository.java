package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skillbox.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int userId);

    boolean existsByEmail(String email);

    boolean existsByName(String name);


}
