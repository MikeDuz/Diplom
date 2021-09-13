package skillbox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.dto.userDto.UserWrapper;
import skillbox.dto.userDto.LogRequest;
import skillbox.dto.captcha.CaptchaDto;
import skillbox.dto.userDto.RegisterDto;
import skillbox.dto.WrapperResponse;
import skillbox.dto.userDto.RestoreDto;
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
    public ResponseEntity<UserWrapper> authCheck(Principal principal) {
        return new ResponseEntity<>(userService.getAuthCheck(principal), HttpStatus.OK);
    }

    @GetMapping("captcha")
    public CaptchaDto getCaptcha() throws IOException {
        return captchaService.getCaptcha();
    }

    @PostMapping("register")
    public ResponseEntity<WrapperResponse> registerUser(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(userService.regUser(registerDto), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<UserWrapper> login(@RequestBody LogRequest logRequest ) {
        return new ResponseEntity<>(userService.loginResponse(logRequest), HttpStatus.OK);
    }

    @GetMapping("logout")
    public ResponseEntity<UserWrapper> logout() {
        return new ResponseEntity<>(userService.logoutResponse(), HttpStatus.OK);
    }

//    @GetMapping("password")
//    public ResponseEntity<String> changePassword() {
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }

    @PostMapping("restore")
    public ResponseEntity<WrapperResponse> restorePassword(@RequestBody RestoreDto email) {
        return new ResponseEntity<>(userService.restorePass(email), HttpStatus.OK);
    }


}
