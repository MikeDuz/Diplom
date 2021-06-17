package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skillbox.entity.PostComments;

public interface PostCommentsRepository extends JpaRepository<PostComments, Integer> {
}
