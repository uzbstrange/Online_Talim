package online_ta_lim.controller;

import online_ta_lim.custom_responses.ApiResponse;
import online_ta_lim.dto.UserLoginDto;
import online_ta_lim.dto.UserRegistrationDto;
import online_ta_lim.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registerDto){
        ApiResponse resp = authService.register(registerDto);
        return ResponseEntity.status(resp.isSuccess()?200:400).body(resp);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserLoginDto loginDto){
//        ApiResponse resp = authService.login(loginDto);
//        return ResponseEntity.ok(resp);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto loginDto) {
        ApiResponse resp = authService.login(loginDto);
        if (resp.isSuccess()) {
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
        }
    }

}
