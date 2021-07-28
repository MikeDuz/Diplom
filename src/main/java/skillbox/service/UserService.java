package skillbox.service;

import skillbox.dto.DtoWrapper;
import skillbox.dto.userDto.LogRequest;
import skillbox.dto.userDto.RegisterDto;
import skillbox.dto.userDto.RegisterResponse;

import java.security.Principal;

public interface UserService  {

    RegisterResponse regUser(RegisterDto regUser);

    DtoWrapper loginResponse(LogRequest logRequest);

    DtoWrapper getAuthCheck(Principal principal);

    DtoWrapper logoutResponse();


}
