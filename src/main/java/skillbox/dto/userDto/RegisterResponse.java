package skillbox.dto.userDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {

    boolean result;
    BadRegister errors;
}
