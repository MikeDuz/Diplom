package skillbox.service;

import org.springframework.stereotype.Service;
import skillbox.api.response.AuthCheckResponse;

@Service
public class AuthCheckService {

    public AuthCheckResponse getAuthCheck() {
        AuthCheckResponse authCheck = new AuthCheckResponse();
        authCheck.setResult(false);
        return authCheck;
    }


}
