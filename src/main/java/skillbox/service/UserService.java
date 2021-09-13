package skillbox.service;

import skillbox.dto.userDto.RestoreDto;
import skillbox.dto.userDto.UserWrapper;
import skillbox.dto.userDto.LogRequest;
import skillbox.dto.userDto.RegisterDto;
import skillbox.dto.WrapperResponse;

import java.security.Principal;

public interface UserService  {

    WrapperResponse regUser(RegisterDto regUser);

    UserWrapper loginResponse(LogRequest logRequest);

    UserWrapper getAuthCheck(Principal principal);

    UserWrapper logoutResponse();

    WrapperResponse restorePass(RestoreDto email);


}
