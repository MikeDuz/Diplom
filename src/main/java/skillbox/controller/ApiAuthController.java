package skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.dto.authCheck.AuthDTO;
import skillbox.dto.captcha.CaptchaDto;
import skillbox.dto.userDto.RegisterDto;
import skillbox.dto.userDto.RegisterResponse;
import skillbox.service.AuthCheckService;
import skillbox.service.CaptchaService;
import skillbox.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class ApiAuthController {

    private final AuthCheckService authCheck;
    private final CaptchaService captchaService;
    private final UserService userService;

    @GetMapping("check")
    public AuthDTO authCheck() {
        return authCheck.getAuthCheck();
    }

    @GetMapping("captcha")
    public CaptchaDto getCaptcha() throws IOException {
        return captchaService.getCaptcha();
    }

    @PostMapping("register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<RegisterResponse>(userService.regUser(registerDto), HttpStatus.OK);
    }

}
