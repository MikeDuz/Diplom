package skillbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import skillbox.entity.CaptchaCodes;

import java.time.LocalDateTime;

public interface CaptchaRepository extends JpaRepository<CaptchaCodes, Integer> {

    @Modifying
    void deleteAllByTimeBefore(LocalDateTime time);

    CaptchaCodes findOneBySecretCode(String secretCode);

}
