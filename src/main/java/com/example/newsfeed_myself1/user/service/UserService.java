package com.example.newsfeed_myself1.user.service;

import com.example.newsfeed_myself1.auth.dto.LoginRequestDto;
import com.example.newsfeed_myself1.auth.dto.SignupRequestDto;
import com.example.newsfeed_myself1.user.dto.PasswordUpdateRequestDto;
import com.example.newsfeed_myself1.user.dto.ProfileUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import com.example.newsfeed_myself1.user.entity.User;
import com.example.newsfeed_myself1.user.dto.UserResponseDto;
import com.example.newsfeed_myself1.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public void signup(SignupRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getEmail(), requestDto.getPassword(), requestDto.getUsername());
    userRepository.save(user);
    }

    // 로그인
    public User login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자가 없습니다."));

        if(!user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }

    // 프로필 조회
    // ID로 조회, null 체크
    public UserResponseDto getUserProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        return new UserResponseDto(user);
    }

    // 프로필 수정
    // 로그인한 사용자 프로필 수정
    public void updateProfile(Long userId, ProfileUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다."));

        user.updateProfile(requestDto.getUsername(), requestDto.getProfileImage(), requestDto.getBio());
    }

    // 비밀번호 변경
    public void updatePassword(Long userId, PasswordUpdateRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        String current = dto.getCurrentPassword();
        String next = dto.getNewPassword();

        // 비밀번호 일치하지않음
        if(!passwordEncoder.matches(current,user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 기존 비밀번호와 동일
        if(passwordEncoder.matches(next,user.getPassword())) {
            throw new IllegalArgumentException("동일한 비밀번호로 변경할 수 없습니다.");
        }

        // 최소 8자 이상
        if(next == null || next.length() < 8) {
            throw new IllegalArgumentException("최소 8자 이상이어야 합니다.");
        }
        user.updatePassword(passwordEncoder.encode(next));
    }
}
