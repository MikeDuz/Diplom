package skillbox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skillbox.entity.Post;
import skillbox.entity.Tag2Post;


import java.util.List;


@Repository
public interface Tag2PostRepository extends JpaRepository<Tag2Post, Integer> {

    @Query("select t.tagId.id as tagId,t.tagId.name as tagName, count(distinct t.postId) as postCount from Tag2Post t group by t.tagId order by postCount desc")
    List<TagPostCount> getTagPostCounts();

    @Query("select t.postId from Tag2Post t where t.tagId.name = ?1")
    Page<Post> findPostByTag(String tag, Pageable paging);

    @Query("from Tag2Post t where t.postId.id = ?1")
    List<Tag2Post> findTagByPost(int postId);

    @Query("select count(t.id) from Tag2Post t where t.tagId.name like concat('%', ?1, '%')")
    int countAllByTagIdContains(String tag);
}
