package com.example.newsfeed_myself1.auth.controller;

import com.example.newsfeed_myself1.auth.dto.LoginRequestDto;
import com.example.newsfeed_myself1.auth.dto.LoginResponseDto;
import com.example.newsfeed_myself1.auth.dto.SignupRequestDto;
import com.example.newsfeed_myself1.global.jwt.JwtService;
import com.example.newsfeed_myself1.user.service.UserService;
import com.example.newsfeed_myself1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        // 이메일/비밀번호 검증
        User user = userService.login(requestDto);
        // 토큰 생성
        String token = jwtService.createJwt(user.getId());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

}
