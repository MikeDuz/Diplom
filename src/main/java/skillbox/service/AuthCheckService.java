package skillbox.service;

import org.springframework.stereotype.Service;
import skillbox.dto.authCheck.AuthDTO;

@Service
public class AuthCheckService {

    public AuthDTO getAuthCheck() {
        AuthDTO authResponce = new AuthDTO();
        authResponce.setResult(false);
        return authResponce;
    }


}
