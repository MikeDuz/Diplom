package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.WrapperResponse;
import skillbox.dto.userDto.*;
import skillbox.entity.CaptchaCodes;
import skillbox.entity.enums.ModerationStatus;
import skillbox.mapping.UserMapper;
import skillbox.repository.CaptchaRepository;
import skillbox.repository.PostRepository;
import skillbox.repository.UserRepository;
import skillbox.service.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRep;
    private final CaptchaRepository capRep;
    private final AuthenticationManager authManager;
    private final PostRepository postRep;

    private final SecureRandom secureRandom = new SecureRandom();
    private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    @Value("${blog.baseURL}")
    public static String BASEURL;
    private final JavaMailSender emailSender;

    @Override
    @Transactional
    public WrapperResponse regUser(RegisterDto regUser) {
        WrapperResponse response = new WrapperResponse();
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
    public UserWrapper loginResponse(LogRequest logRequest) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        logRequest.getEmail(),
                        logRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        UserWrapper dtoWrap = new UserWrapper();
        return getDtoWrapper(user.getUsername(), dtoWrap);
    }

    @Override
    public UserWrapper getAuthCheck(Principal principal) {
        UserWrapper dtoWrap = new UserWrapper();
        if (principal == null) {
            dtoWrap.setResult(false);
            return dtoWrap;
        }
        return getDtoWrapper(principal.getName(), dtoWrap);
    }

    @Override
    public UserWrapper logoutResponse() {
        UserWrapper dtoWrap = new UserWrapper();
        dtoWrap.setResult(true);
        return dtoWrap;
    }

    @Override
    public WrapperResponse restorePass(RestoreDto email)  {
        WrapperResponse response = new WrapperResponse();
        try {
            Optional<skillbox.entity.User> userOptional = userRep.findByEmail(email.getEmail());
            if (userOptional == null) {
                response.setResult(false);
                return response;
            }
            String token = generateNewToken();
            skillbox.entity.User user = userOptional.get();
            userRep.setCode(token, user.getId());
            sendEmail(user.getEmail(), token);
            response.setResult(true);
        } catch(Exception ex) {
            System.out.println(ex);
        }
        return response;
    }


    private UserWrapper getDtoWrapper(String email, UserWrapper dtoWrap) {
        skillbox.entity.User userEntity = userRep
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        int count = postRep.countAllByModerationStatusAndActive(email, true, ModerationStatus.NEW);
        if (!userEntity.isModerator()) {
            return UserMapper.mapUserToResponse(dtoWrap, userEntity, count, false);
        }
        count = getPostCount(ModerationStatus.NEW);
        return UserMapper.mapUserToResponse(dtoWrap, userEntity, count, true);
    }


    private int getPostCount(ModerationStatus modStatus) {
        return postRep.countAllByModerationStatus(modStatus);
    }


    private String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    private String generateEmailText(String token) {
        return "<p><a href=\"" + BASEURL + "/login/change-password/" +
                token + "\">Нажмите на ссылку для восстановления пароля на ресурсе wdbl</a></p>";
    }

    private void sendEmail(String email, String token) throws MessagingException {
        MimeMessage htmlMessage = emailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(htmlMessage);
        message.setFrom("noreply@wdbl.com");
        message.setTo(email);
        message.setSubject("Письмо для восстановления пароля на wdbl.herokuapp.com");
        message.setText(generateEmailText(token), true);
        emailSender.send(htmlMessage);

    }

}
