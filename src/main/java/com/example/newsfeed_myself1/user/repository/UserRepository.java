package com.example.newsfeed_myself1.user.repository;
import com.example.newsfeed_myself1.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// JPA 자동 구현 -> 인터페이스
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);


}
