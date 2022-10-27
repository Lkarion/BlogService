package com.example.blogservice.repository;

import com.example.blogservice.entity.BlogPost;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.status.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findAllByStatus(PostStatus status);
//    List<BlogPost> findAllByOrderByCreatedOnDesc();
//    List<BlogPost> findAllByStatus_PublishedOrderByCreatedOnDesc();
//    List<BlogPost> findAllByBlogUserUsernameAndStatus(String username, PostStatus status);
    List<BlogPost> findAllByBlogUserAndStatus(BlogUser blogUser, PostStatus status);
    List<BlogPost> findAllByBlogUser(BlogUser blogUser);
    Long countByBlogUserAndStatus(BlogUser blogUser, PostStatus status);
}
