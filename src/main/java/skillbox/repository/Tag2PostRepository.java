package skillbox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skillbox.entity.Post;
import skillbox.entity.Tag2Post;
import skillbox.entity.projection.PostProjection;
import skillbox.entity.projection.TagPostCount;


import java.util.List;


@Repository
public interface Tag2PostRepository extends JpaRepository<Tag2Post, Integer> {

    @Query("select t.tagId.id as tagId,t.tagId.name as tagName, count(distinct t.postId) as postCount from Tag2Post t group by t.tagId order by postCount desc")
    List<TagPostCount> getTagPostCounts();

    @Query("select t.postId as id, " +
            "p.time as time, " +
            "p.userId as user, " +
            "p.title as title, " +
            "p.text as text, " +
            "p.viewCount as viewCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value > 0) as likeCount, " +
            "(select count(v.value) from PostVotes v where v.postId.id = p.id and v.value < 0) as dislikeCount, " +
            "(select count(c.id) from PostComments c where c.postId.id = p.id) as commentCount " +
            "from Tag2Post t, Post p " +
            "where t.tagId.name = ?1 and " +
            "p.id = t.postId.id")
    List<PostProjection> findPostByTag(String tag, Pageable paging);

    @Query("from Tag2Post t where t.postId.id = ?1")
    List<Tag2Post> findTagByPost(int postId);

    @Query("select count(t.id) from Tag2Post t where t.tagId.name like concat('%', ?1, '%')")
    int countAllByTagIdContains(String tag);
}
