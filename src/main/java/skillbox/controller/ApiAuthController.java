package skillbox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.dto.DtoWrapper;
import skillbox.dto.userDto.LogRequest;
import skillbox.dto.captcha.CaptchaDto;
import skillbox.dto.userDto.RegisterDto;
import skillbox.dto.userDto.RegisterResponse;
import skillbox.service.UserService;
import skillbox.service.impl.CaptchaService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class ApiAuthController {

    private final CaptchaService captchaService;
    private final UserService userService;

    @GetMapping("check")
    public ResponseEntity<DtoWrapper> authCheck(Principal principal) {
        return new ResponseEntity<>(userService.getAuthCheck(principal), HttpStatus.OK);
    }

    @GetMapping("captcha")
    public CaptchaDto getCaptcha() throws IOException {
        return captchaService.getCaptcha();
    }

    @PostMapping("register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(userService.regUser(registerDto), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<DtoWrapper> login(@RequestBody LogRequest logRequest ) {
        return new ResponseEntity<>(userService.loginResponse(logRequest), HttpStatus.OK);
    }

    @GetMapping("logout")
    public ResponseEntity<DtoWrapper> logout() {
        return new ResponseEntity<>(userService.logoutResponse(), HttpStatus.OK);
    }

}
