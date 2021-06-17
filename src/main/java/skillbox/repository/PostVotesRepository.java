package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skillbox.entity.PostVotes;

public interface PostVotesRepository extends JpaRepository<PostVotes, Integer> {

    @Query( "SELECT COUNT(value) FROM PostVotes WHERE id = :id and value = :val")
    int findAllLike(@Param("val") int value, @Param("id") int id);
}