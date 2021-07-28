package skillbox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import skillbox.entity.Post;
import skillbox.entity.enums.ModerationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select p.time as date from Post p where p.moderationStatus = 'ACCEPTED' and p.isActive = true and p.time < ?1 ")
    List<LocalDateTime> findAllTime(LocalDateTime dateNow);

    @Query("from Post p where p.moderationStatus = 'ACCEPTED' and p.isActive = true and p.time < ?1 ")
    Page<Post> findAll(LocalDateTime dateNow, Pageable paging);

    @Modifying
    @Transactional
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = ?1")
    void incrViewCount(int postId);

    @Query("from Post p where date(p.time) = ?1")
    Page<Post> findAllByTime(Date date, Pageable paging);

    @Query("select count(p.id) from Post p where date(p.time) = ?1")
    Integer countAllByDate(Date date);

    @Query("from Post p where p.text like concat('%', ?1, '%') or p.title like concat('%', ?1, '%')")
    Page<Post> findAllByTextContainsOrTitleContains(String query, Pageable paging);

    @Query("select count(p.id) from Post p where p.text like concat('%', ?1, '%') or p.title like concat('%', ?1, '%')")
    int countAllByTitleContainsAndTextContains(String query);

    int countAllByModerationStatus(ModerationStatus modStatus);

    @Query("select count(p.userId.email) from Post p where p.userId.email = ?1 and p.isActive = ?2 and p.moderationStatus = ?3")
    int countAllByModerationStatusAndActive(String email, boolean isActive, ModerationStatus modStatus);

    @Query("select count(p.userId.email) from Post p where p.isActive = ?1 and p.userId.email = ?2")
    int countMyPostByActive(boolean isActive, String email);

    @Query("from Post p where p.userId.email = ?1 and p.isActive = ?2 and p.moderationStatus = ?3")
    Page<Post> findMyPosts(String email, boolean isActive, ModerationStatus modStatus, Pageable paging);

    @Query("from Post p where p.isActive = ?1 and p.userId.email = ?2")
    Page<Post> findMyPostInactive(boolean isActive, String email, Pageable paging);
}
