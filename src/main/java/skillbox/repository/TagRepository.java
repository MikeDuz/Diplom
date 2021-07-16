package skillbox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skillbox.entity.Post;
import skillbox.entity.Tags;


@Repository
public interface TagRepository extends JpaRepository<Tags, Integer> {

}
