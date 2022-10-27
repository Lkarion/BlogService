package com.example.blogservice.repository;

import com.example.blogservice.entity.BlogPost;
import com.example.blogservice.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Boolean existsByText(String text);

    List<Tag> findAllByPostsContaining(BlogPost blogPost);
}
