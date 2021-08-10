package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skillbox.entity.Tag;


@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    boolean existsByName(String tag);

    Tag findOneByName(String name);
}
