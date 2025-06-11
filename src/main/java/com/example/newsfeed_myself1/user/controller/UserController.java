package com.example.newsfeed_myself1.user.controller;
import com.example.newsfeed_myself1.user.dto.PasswordUpdateRequestDto;
import com.example.newsfeed_myself1.user.dto.ProfileUpdateRequestDto;
import com.example.newsfeed_myself1.user.dto.UserResponseDto;
import com.example.newsfeed_myself1.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 프로필 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserProfile(@PathVariable Long id) {
        // UserService에서 id 조회
        UserResponseDto userProfile = userService.getUserProfile(id);

        // 200 OK
        return ResponseEntity.ok(userProfile);
    }

    // 프로필 수정
    // 로그인 한 사람만 가능
    public ResponseEntity<String> updateProfile(@RequestBody ProfileUpdateRequestDto requestDto, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");

        // 예외처리
        if(userId == null ) {
            return ResponseEntity.status(401).body("로그인이 필요한 기능입니다.");
        }
        userService.updateProfile(userId, requestDto);
        return ResponseEntity.ok("프로필 수정이 완료되었습니다.");
    }

    // 비밀번호 변경
    public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateRequestDto dto, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if(userId == null) {
            return ResponseEntity.status(401).body("로그인이 필요한 서비스입니다.");
        }

        userService.updatePassword(userId, dto);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }


}
