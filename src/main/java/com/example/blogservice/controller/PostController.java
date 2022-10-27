package com.example.blogservice.controller;

import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.dto.postDTOs.*;
import com.example.blogservice.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public List<PostResponseShortDTO> getAllBlogPosts(@AuthenticationPrincipal KeyDTO keyDTO) {
        return postService.getAllBlogPosts(keyDTO);
    }

    @PostMapping("/posts/create")
    public PostResponseDTO createBlogPost(@RequestBody PostRequestDTO request) {
        return postService.createBlogPost(request);
    }

    @GetMapping("/posts/search")
    public List<PostResponseShortDTO> findBlogPosts(@RequestBody PostSearchRequestDTO request) {
        return postService.findBlogPosts(request);
    }

    @GetMapping("/posts/{id}")
    public PostResponseFullDTO getBlogPostById(@AuthenticationPrincipal KeyDTO keyDTO,
            @PathVariable(name = "id") Long postId) {
        return postService.getBlogPostById(keyDTO, postId);
    }

    @PatchMapping("/posts/{id}")
    public void updateBlogPost(@AuthenticationPrincipal KeyDTO keyDTO,
                               @PathVariable(name = "id") Long postId,
                           @RequestBody PostRequestDTO request) {
        postService.updateBlogPost(keyDTO, postId, request);
    }

//    @userRepository.findByUsername()
    @PreAuthorize("hasAuthority('users.posts.rw')")
    @PutMapping("/posts/{id}/publish")
    public void publishBlogPostById(@AuthenticationPrincipal KeyDTO keyDTO,
                                        @PathVariable(name = "id") Long postId) {
        postService.publishBlogPostById(keyDTO, postId);
    }

    @PutMapping("/posts/{id}/unpublish")
    public void unpublishBlogPostById(@AuthenticationPrincipal KeyDTO keyDTO,
                                      @PathVariable(name = "id") Long postId) {
        postService.unpublishBlogPostById(keyDTO, postId);
    }

    @PreAuthorize("hasAuthority('admin.posts.rw')")
    @PutMapping("/posts/{id}/block")
    public void toggleBlockOrPublishBlogPostById(@PathVariable(name = "id") Long postId) {
        postService.toggleBlockOrPublishBlogPostById(postId);
    }

    @GetMapping("/posts/user/{username}")
    public List<PostResponseShortDTO> getBlogPostsByUsername(@AuthenticationPrincipal KeyDTO keyDTO,
            @PathVariable(name = "username") String username) {
        return postService.getBlogPostsByUsername(keyDTO, username);
    }

    @DeleteMapping("/posts/{id}")
    public void deleteBlogPostById(@AuthenticationPrincipal KeyDTO keyDTO,
                                   @PathVariable(name = "id") Long postId) {
        postService.deleteBlogPostById(keyDTO, postId);
    }

}
