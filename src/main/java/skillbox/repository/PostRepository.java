package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import skillbox.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select p.time as date from Post p where p.moderationStatus = 'ACCEPTED' and p.isActive = true and p.time < ?1 ")
    List<LocalDateTime> findAllTime(LocalDateTime dateNow);

    @Query("from Post p where p.moderationStatus = 'ACCEPTED' and p.isActive = true and p.time < ?1 ")
    List<Post> findAll(LocalDateTime dateNow);

    @Modifying
    @Transactional
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = ?1")
    void incrViewCount(int postId);
}
