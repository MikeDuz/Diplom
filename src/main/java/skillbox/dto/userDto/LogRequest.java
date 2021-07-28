package skillbox.dto.userDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogRequest {

    @JsonProperty("e_mail")
    private String email;
    private String password;
}
