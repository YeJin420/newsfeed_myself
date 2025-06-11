package com.example.newsfeed_myself1.user.dto;

import lombok.Getter;

@Getter
public class PasswordUpdateRequestDto {

    private String currentPassword;
    private String newPassword;

}
