package skillbox.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import skillbox.entity.Post;
import skillbox.entity.Tag;
import skillbox.entity.enums.ModerationStatus;
import skillbox.entity.projection.PostProjection;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query ("select p.id as id, " +
            "p.time as time, " +
            "p.userId as user, " +
            "p.title as title, " +
            "p.text as text, " +
            "p.viewCount as viewCount, " +
            "p.comments.size as commentCount, " +
            "(select count(v.value) as likeCount from PostVotes v where v.postId.id = p.id and v.value > 0) as likeCount, " +
            "(select count(v.value) as dislikeCount from PostVotes v where v.postId.id = p.id and v.value < 0) as dislikeCount " +
            "from Post p where p.moderationStatus = ?1 and p.isActive = ?2 and p.time < ?3")
    List<PostProjection> getPosts(ModerationStatus modStatus, boolean isActive, LocalDateTime time, Pageable page);

    @Query("select count(p.id) from Post p where p.moderationStatus = ?1 and p.isActive = ?2 and p.time < ?3")
    int countPosts(ModerationStatus modStatus, boolean isActive, LocalDateTime time);

    @Query("select p.time as date from Post p where p.moderationStatus = 'ACCEPTED' and p.isActive = true and p.time < ?1 ")
    List<LocalDateTime> findAllTime(LocalDateTime dateNow);

    @Modifying
    @Transactional
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = ?1")
    void incrViewCount(int postId);

    @Query("select p.id as id, " +
            "p.time as time, " +
            "p.userId as user, " +
            "p.title as title, " +
            "p.text as text, " +
            "p.viewCount as viewCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value > 0) as likeCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value < 0) as dislikeCount, " +
            "p.comments.size as commentCount " +
            "from Post p where date(p.time) = ?1")
    List<PostProjection> findAllByTime(Date date, Pageable paging);

    @Query("select count(p.id) from Post p where date(p.time) = ?1")
    Integer countAllByDate(Date date);

    @Query("select p.id as id, " +
            "p.time as time, " +
            "p.userId as user, " +
            "p.title as title, " +
            "p.text as text, " +
            "p.viewCount as viewCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value > 0) as likeCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value < 0) as dislikeCount, " +
            "p.comments.size as commentCount " +
            "from Post p " +
            "where p.text like concat('%', ?1, '%') or p.title like concat('%', ?1, '%')")
    List<PostProjection> findAllByTextContainsOrTitleContains(String query, Pageable paging);

    Post findPostById(int id);

    @Query("select count(p.id) from Post p where p.text like concat('%', ?1, '%') or p.title like concat('%', ?1, '%')")
    int countAllByTitleContainsAndTextContains(String query);

    int countAllByModerationStatus(ModerationStatus modStatus);

    @Query("select count(p.userId.email) from Post p where p.userId.email = ?1 and p.isActive = ?2 and p.moderationStatus = ?3")
    int countAllByModerationStatusAndActive(String email, boolean isActive, ModerationStatus modStatus);

    @Query("select count(p.userId.email) from Post p where p.isActive = ?1 and p.userId.email = ?2")
    int countMyPostByActive(boolean isActive, String email);

    @Query ("select p.id as id, " +
            "p.time as time, " +
            "p.userId as user, " +
            "p.title as title, " +
            "p.text as text, " +
            "p.viewCount as viewCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value > 0) as likeCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value < 0) as dislikeCount, " +
            "p.comments.size as commentCount " +
            "from Post p " +
            "where p.userId.email = ?1 and " +
            "p.isActive = ?2 and " +
            "p.moderationStatus = ?3")
    List<PostProjection> findMyPosts(String email, boolean isActive, ModerationStatus modStatus, Pageable paging);

    @Query("select count(p.id) from Post p where p.isActive = ?1 and p.moderationStatus = ?2")
    int countPostsByIsActiveAndModerationStatus(boolean isActive, ModerationStatus status);

    @Query ("select p.id as id, " +
            "p.time as time, " +
            "p.userId as user, " +
            "p.title as title, " +
            "p.text as text, " +
            "p.viewCount as viewCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value > 0) as likeCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value < 0) as dislikeCount, " +
            "p.comments.size as commentCount " +
            "from Post p " +
            "where p.moderatorId.email = ?1 and " +
            "p.isActive = ?2 and " +
            "p.moderationStatus = ?3")
    List<PostProjection> findPostByModeration(String email, boolean isActive, ModerationStatus modStatus, Pageable paging);

    @Query("select p.id as id, " +
            "p.time as time, " +
            "p.userId as user, " +
            "p.title as title, " +
            "p.text as text, " +
            "p.viewCount as viewCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value > 0) as likeCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value < 0) as dislikeCount, " +
            "p.comments.size as commentCount " +
            "from Post p " +
            "where p.moderationStatus = ?1")
    List<PostProjection> findAllByModerationStatus(ModerationStatus modStatus, Pageable paging);

    @Query("select p.userId.email from Post p where p.id = ?1")
    String findEmailById(int postId);

    @Modifying
    @Transactional
    @Query("update Post set " +
            "time = :time, " +
            "isActive = :active, " +
            "title = :title, " +
            "text = :text, " +
            "tags = :tags, " +
            "moderationStatus = :modStatus " +
            "where id = :id")
    void updatePostWithModStatus(
            @Param("id") int id,
            @Param("time") LocalDateTime time,
            @Param("active") boolean isActive,
            @Param("modStatus") ModerationStatus modStatus,
            @Param("title") String title,
            @Param("tags") Set<Tag> tags,
            @Param("text") String text);

}
