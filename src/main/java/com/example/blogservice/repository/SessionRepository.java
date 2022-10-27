package com.example.blogservice.repository;

import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.BlogUserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<BlogUserSession, Long> {

    BlogUserSession findBySessionId(String sessionId);
    List<BlogUserSession> findAllByBlogUser(BlogUser user);
    void deleteBySessionId(String sessionId);
}
