package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skillbox.entity.Tag2Post;
import skillbox.entity.Tags;
import skillbox.util.TagCount;

import java.util.List;
import java.util.Map;

@Repository
public interface TagRepository extends JpaRepository<Tags, Integer> {
    List<Tags> findByName(String name);
    @Query("SELECT postId, COUNT(tagId) FROM Tag2Post GROUP BY tagId ORDER BY postId DESC")
    Map<Integer, Integer> findCountTag();

}
