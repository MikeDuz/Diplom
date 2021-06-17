package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skillbox.entity.GlobalSettings;

public interface GlobalSettingRepository extends JpaRepository<GlobalSettings, Integer> {

}
