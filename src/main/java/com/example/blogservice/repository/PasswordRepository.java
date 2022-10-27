package com.example.blogservice.repository;

import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.BlogUserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<BlogUserPassword, Long> {

    BlogUserPassword findByBlogUser(BlogUser blogUser);
}
