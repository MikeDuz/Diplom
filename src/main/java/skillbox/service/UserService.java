package skillbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.userDto.BadRegister;
import skillbox.dto.userDto.RegisterDto;
import skillbox.dto.userDto.RegisterResponse;
import skillbox.entity.CaptchaCodes;
import skillbox.mapping.UserMapper;
import skillbox.repository.CaptchaRepository;
import skillbox.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRep;
    private final CaptchaRepository capRep;

    @Transactional
    public RegisterResponse regUser(RegisterDto regUser) {
        RegisterResponse response = new RegisterResponse();
        BadRegister badRequest = new BadRegister();
        if (userRep.existsByEmail(regUser.getEmail())) {
            response.setResult(false);
            badRequest.setEmail("Этот e-mail уже зарегистрирован");
            response.setErrors(badRequest);
            return response;
        }
        if (userRep.existsByName(regUser.getName())) {
            response.setResult(false);
            badRequest.setName("Это имя недоступно");
            response.setErrors(badRequest);
            return response;
        }
        if (regUser.getPassword().length() < 6) {
            response.setResult(false);
            badRequest.setPassword("Пароль короче 6 символов");
            response.setErrors(badRequest);
            return response;
        }
        if (checkCaptcha(regUser)) {
            response.setResult(false);
            badRequest.setCaptcha("неправильно введен код с картинки");
            response.setErrors(badRequest);
            return response;
        }
        userRep.save(UserMapper.regMapUser(regUser));
        response.setResult(true);
        return response;
    }

    private boolean checkCaptcha(RegisterDto regUser) {
        CaptchaCodes captcha = capRep.findOneBySecretCode(regUser.getCaptchaSecret());
        return !captcha.getCode().equals(regUser.getCaptcha());
    }

}
