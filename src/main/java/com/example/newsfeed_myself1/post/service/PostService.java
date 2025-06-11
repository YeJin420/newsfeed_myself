package com.example.newsfeed_myself1.post.service;

import com.example.newsfeed_myself1.post.dto.PostRequestDto;
import com.example.newsfeed_myself1.post.dto.PostResponseDto;
import com.example.newsfeed_myself1.post.entity.Post;
import com.example.newsfeed_myself1.post.repository.PostRepository;
import com.example.newsfeed_myself1.user.entity.User;
import com.example.newsfeed_myself1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 생성
    public void createPost(Long userId, PostRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        Post post = new Post(dto.getTitle(), dto.getContent(), user);
        postRepository.save(post);
    }

    // 게시글 수정
    public void updatePost(Long postId, Long userId, PostRequestDto dto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 게시글만 수정할 수 있습니다.");
        }

        post.update(dto.getTitle(), dto.getContent());
    }

    // 게시글 삭제
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 게시글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    // 게시글 전체 조회 (페이징, 정렬 포함)
    public Page<PostResponseDto> getPostList(int page, int size) {
        return postRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        ).map(PostResponseDto::new);
    }

}
