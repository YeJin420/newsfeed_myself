package com.example.newsfeed_myself1.post.repository;

import com.example.newsfeed_myself1.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
