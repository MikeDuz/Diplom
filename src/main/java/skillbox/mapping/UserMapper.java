package skillbox.mapping;

import skillbox.dto.userDto.RegisterDto;
import skillbox.entity.User;

import java.time.LocalDateTime;

public class UserMapper {

    public static User regMapUser(RegisterDto regUser) {
        User user = new User();
                user.setModerator(false);
                user.setRegTime(LocalDateTime.now());
                user.setName(regUser.getName());
                user.setEmail(regUser.getEmail());
                user.setPassword(regUser.getPassword());
            return user;
    }
}
