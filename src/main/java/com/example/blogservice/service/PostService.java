package com.example.blogservice.service;

import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.dto.postDTOs.*;

import java.util.List;

public interface PostService {

    List<PostResponseShortDTO> getAllBlogPosts(KeyDTO keyDTO);
    PostResponseDTO createBlogPost(PostRequestDTO request);
    List<PostResponseShortDTO> findBlogPosts(PostSearchRequestDTO request);
    PostResponseFullDTO getBlogPostById(KeyDTO keyDTO, Long postId);
    void updateBlogPost(KeyDTO keyDTO, Long postId, PostRequestDTO request);
    void publishBlogPostById(KeyDTO keyDTO, Long postId);
    void unpublishBlogPostById(KeyDTO keyDTO, Long postId);
    void toggleBlockOrPublishBlogPostById(Long postId);
    List<PostResponseShortDTO> getBlogPostsByUsername(KeyDTO keyDTO, String username);
    void deleteBlogPostById(KeyDTO keyDTO, Long postId);

}
