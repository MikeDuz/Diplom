package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import skillbox.entity.PostVotes;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostVotesRepository extends JpaRepository<PostVotes, Integer> {

    @Query( "SELECT COUNT(value) FROM PostVotes WHERE id = :id and value = :val")
    int findAllLike(@Param("val") int value, @Param("id") int id);

    @Query("from PostVotes p where p.postId.id = ?1 and p.userId.id = ?2")
    Optional<PostVotes> findByPostIdAndUserId(int postId, int userId);

    @Modifying
    @Transactional
    @Query("update PostVotes set value = ?2, time = ?3 where id = ?1")
    void updatePostVotes(int id, int value, LocalDateTime time);


}