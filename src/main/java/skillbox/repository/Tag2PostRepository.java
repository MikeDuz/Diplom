package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skillbox.entity.Tag2Post;


import java.util.List;


@Repository
public interface Tag2PostRepository extends JpaRepository<Tag2Post, Integer> {
    @Query("SELECT t.id AS tagId, COUNT(t.tagId) AS postCount FROM Tag2Post t ORDER BY postCount DESC")
    List<TagPostCount> getTagPostCounts();
}
