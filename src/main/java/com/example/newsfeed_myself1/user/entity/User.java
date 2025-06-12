package com.example.newsfeed_myself1.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column
    private String password;

    private String profileImage;
    private String bio;

    // 회원가입
    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    // 프로필 수정
    public void updateProfile(String username, String profileImage, String bio) {
        this.username = username;
        this.profileImage = profileImage;
        this.bio = bio;
    }

    // 비밀번호 변경
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

}
