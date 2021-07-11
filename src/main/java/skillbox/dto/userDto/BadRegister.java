package skillbox.dto.userDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRegister {

    String email;
    String name;
    String password;
    String captcha;
}
