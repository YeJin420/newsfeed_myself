package com.example.newsfeed_myself1.user.dto;

import lombok.Getter;

@Getter
public class ProfileUpdateRequestDto {
    private String username;
    private String profileImage;
    private String bio;
}
