package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skillbox.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int userId);


}
