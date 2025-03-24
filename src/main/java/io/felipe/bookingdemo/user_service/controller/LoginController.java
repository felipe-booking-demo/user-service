package io.felipe.bookingdemo.user_service.controller;

import io.felipe.bookingdemo.user_service.dto.LoginDTO;
import io.felipe.bookingdemo.user_service.dto.TokenDTO;
import io.felipe.bookingdemo.user_service.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@Validated
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(this.loginService.authenticate(loginDTO));
    }
}
