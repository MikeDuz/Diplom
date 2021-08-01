package skillbox.mapping;

import skillbox.config.SecurityConfig;
import skillbox.dto.userDto.UserWrapper;
import skillbox.dto.userDto.RegisterDto;
import skillbox.dto.userDto.UserLoginResponse;
import skillbox.entity.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UserMapper {

    public static User regMapUser(RegisterDto regUser) {
        User user = new User();
        user.setModerator(false);
        user.setRegTime(LocalDateTime.now(ZoneOffset.UTC));
        user.setName(regUser.getName());
        user.setEmail(regUser.getEmail());
        user.setPassword(SecurityConfig.passwordEncoder().encode(regUser.getPassword()));
        return user;
    }

    public static UserWrapper mapUserToResponse(UserWrapper dtoWrap,
                                                User user,
                                                int moderationCount,
                                                boolean settings) {
        dtoWrap.setResult(true);
        UserLoginResponse userResp = UserLoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .photo(user.getPhoto())
                .email(user.getEmail())
                .moderation(user.isModerator())
                .moderationCount(moderationCount)
                .settings(settings)
                .build();
        dtoWrap.setUser(userResp);
        return dtoWrap;
    }


}
