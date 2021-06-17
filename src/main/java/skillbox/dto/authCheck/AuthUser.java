package skillbox.dto.authCheck;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUser {

    int id;
    String name;
    String photo;
    String email;
    boolean moderation;
    int moderationCount;
    boolean setting;

}
