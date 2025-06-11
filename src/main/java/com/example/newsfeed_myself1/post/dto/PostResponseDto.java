package com.example.newsfeed_myself1.post.dto;

import com.example.newsfeed_myself1.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long postId;
    private final String title;
    private final String content;
    private final String username; // 작성자 이름
    private final String createdAt;
    private final String modifiedAt;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUsername();
        this.createdAt = post.getCreatedAt().toString();
        this.modifiedAt = post.getModifiedAt().toString();
    }
}
