package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skillbox.entity.PostComments;

import java.util.List;

public interface PostCommentsRepository extends JpaRepository<PostComments, Integer> {

    @Query("from PostComments p where p.id = ?1")
    List<PostComments> findAllByPostId(int id);
}
