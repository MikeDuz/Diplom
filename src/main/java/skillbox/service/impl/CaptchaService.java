package skillbox.service.impl;

import com.github.cage.Cage;
import com.github.cage.image.Painter;
import com.github.cage.token.RandomTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.captcha.CaptchaDto;
import skillbox.entity.CaptchaCodes;
import skillbox.repository.CaptchaRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CaptchaService {

    private static final RandomTokenGenerator rtg = new RandomTokenGenerator(new Random(), 6);
    private static final Painter p = new Painter(100, 35, null, null, null, null);
    private static final Cage c = new Cage(p, null, null, null, 0.75f, null, null);
    private final CaptchaRepository capRep;
    @Value("${blog.liveTimeCaptcha}")
    private String time;

    @Transactional
    public CaptchaDto getCaptcha() throws IOException {
        CaptchaDto captchaDto = new CaptchaDto();
        String secret = c.getTokenGenerator().next();
        String code = rtg.next();
        String img = Base64.getEncoder().encodeToString(c.draw(code));
        captchaDto.setSecret(secret);
        captchaDto.setImage("data:image/png;base64, " + img);
        saveCaptcha(secret, code);
        deleteCapture();
        return captchaDto;
    }

    private void saveCaptcha(String secret, String code) {
        CaptchaCodes captchaCode = new CaptchaCodes();
        captchaCode.setTime(LocalDateTime.now());
        captchaCode.setCode(code);
        captchaCode.setSecretCode(secret);
        capRep.saveAndFlush(captchaCode);
    }

    private void deleteCapture() {
        int liveTime = Integer.valueOf(time)/10000;
        LocalDateTime time = LocalDateTime.now().minusSeconds(liveTime);
        capRep.deleteAllByTimeBefore(time);
    }

}
