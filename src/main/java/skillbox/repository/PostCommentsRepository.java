package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skillbox.entity.PostComment;

import java.util.List;

public interface PostCommentsRepository extends JpaRepository<PostComment, Integer> {

    @Query("from PostComment p where p.id = ?1")
    List<PostComment> findAllByPostId(int id);
}
