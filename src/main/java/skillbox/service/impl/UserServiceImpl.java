package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.DtoWrapper;
import skillbox.dto.userDto.LogRequest;
import skillbox.dto.userDto.BadRegister;
import skillbox.dto.userDto.RegisterDto;
import skillbox.dto.userDto.RegisterResponse;
import skillbox.entity.CaptchaCodes;
import skillbox.entity.enums.ModerationStatus;
import skillbox.mapping.UserMapper;
import skillbox.repository.CaptchaRepository;
import skillbox.repository.PostRepository;
import skillbox.repository.UserRepository;
import skillbox.service.UserService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRep;
    private final CaptchaRepository capRep;
    private final AuthenticationManager authManager;
    private final PostRepository postRep;

    @Override
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

    @Override
    public DtoWrapper loginResponse(LogRequest logRequest) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        logRequest.getEmail(),
                        logRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        DtoWrapper dtoWrap = new DtoWrapper();
        return getDtoWrapper(user.getUsername(), dtoWrap);
    }

    @Override
    public DtoWrapper getAuthCheck(Principal principal) {
        DtoWrapper dtoWrap = new DtoWrapper();
        if(principal == null) {
           dtoWrap.setResult(false);
           return dtoWrap;
        }
        return getDtoWrapper(principal.getName(), dtoWrap);
    }

    @Override
    public DtoWrapper logoutResponse() {
        DtoWrapper dtoWrap = new DtoWrapper();
        dtoWrap.setResult(true);
        return dtoWrap;
    }

    private DtoWrapper getDtoWrapper(String email, DtoWrapper dtoWrap) {
        skillbox.entity.User userEntity = userRep
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        int count = 0;
        if(!userEntity.isModerator()) {
            return UserMapper.mapUserToResponse(dtoWrap, userEntity, count, false);
        }
        count = getPostCount(ModerationStatus.NEW);
        return UserMapper.mapUserToResponse(dtoWrap, userEntity, count, true);
    }



    private int getPostCount(ModerationStatus modStatus) {
        return postRep.countAllByModerationStatus(modStatus);
    }

}
