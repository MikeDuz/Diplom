package skillbox.api.response;

import lombok.Getter;
import lombok.Setter;
import skillbox.entity.Users;


public class AuthCheckResponse {

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    private boolean result;
    private Users user;

}
