package com.example.newsfeed_myself1.post.controller;

import com.example.newsfeed_myself1.global.jwt.JwtService;
import com.example.newsfeed_myself1.post.dto.PostRequestDto;
import com.example.newsfeed_myself1.post.dto.PostResponseDto;
import com.example.newsfeed_myself1.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        Long userId = jwtService.verifyToken(token);

        PostResponseDto responseDto = postService.createPost(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        Long userId = jwtService.verifyToken(token);

        postService.updatePost(postId, userId, requestDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        Long userId = jwtService.verifyToken(token);

        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

}

// JWT 더 공부하고 세션->JWT로 바꾸기