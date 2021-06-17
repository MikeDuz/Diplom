package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skillbox.entity.Posts;

public interface PostRepository extends JpaRepository<Posts, Integer> {
}
