package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skillbox.entity.Tag2Post;


import java.util.List;


@Repository
public interface Tag2PostRepository extends JpaRepository<Tag2Post, Integer> {
    @Query("select t.tagId.id as tagId,t.tagId.name as tagName, count(distinct t.postId) as postCount from Tag2Post t group by t.tagId order by postCount desc")
    List<TagPostCount> getTagPostCounts();
}
