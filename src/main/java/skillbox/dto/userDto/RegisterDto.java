package skillbox.dto.userDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    @JsonProperty("e_mail")
    String email;
    String password;
    String name;
    String captcha;
    @JsonProperty("captcha_secret")
    String captchaSecret;
}
