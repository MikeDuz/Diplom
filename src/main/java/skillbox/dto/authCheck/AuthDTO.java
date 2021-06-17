package skillbox.dto.authCheck;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {

    boolean result;
    AuthUser user;

}
