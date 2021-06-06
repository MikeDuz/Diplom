package skillbox.api.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import skillbox.dto.authCheck.User;

import java.util.ArrayList;

@Getter
@Setter
@Component
public class PostResponse {

    int count;
    ArrayList<User> posts;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
