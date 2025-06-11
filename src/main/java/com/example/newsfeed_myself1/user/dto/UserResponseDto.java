package com.example.newsfeed_myself1.user.dto;

import com.example.newsfeed_myself1.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long userId;
    private final String username;
    private final String profileImage;
    private final String bio;

    public UserResponseDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.profileImage = user.getProfileImage();
        this.bio = user.getBio();
    }

}
