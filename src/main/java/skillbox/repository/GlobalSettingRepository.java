package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import skillbox.entity.GlobalSettings;
import skillbox.entity.enums.GlobalSettingCode;
import skillbox.entity.enums.GlobalSettingCodeValue;

public interface GlobalSettingRepository extends JpaRepository<GlobalSettings, Integer> {

    @Modifying
    @Transactional
    @Query("update GlobalSettings set value = ?1 where code = ?2 ")
    void updateMultiUserMode(GlobalSettingCodeValue value, GlobalSettingCode code);

}
