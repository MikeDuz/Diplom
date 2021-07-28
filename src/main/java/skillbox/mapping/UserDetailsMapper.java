package skillbox.mapping;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import skillbox.security.UserDetail;

public class UserDetailsMapper {

    public static UserDetails userDetailMapper(skillbox.entity.User userEntity) {
        return new User(userEntity.getEmail(),
                userEntity.getPassword(),
                true, true, true, true,
                userEntity.getRole().getAuthorities());
    }
}
