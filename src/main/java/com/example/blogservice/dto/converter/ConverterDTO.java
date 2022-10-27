package com.example.blogservice.dto.converter;

import com.example.blogservice.dto.loginDTOs.LoginRequestDTO;
import com.example.blogservice.dto.postDTOs.PostRequestDTO;
import com.example.blogservice.dto.postDTOs.PostResponseDTO;
import com.example.blogservice.dto.postDTOs.PostResponseFullDTO;
import com.example.blogservice.dto.postDTOs.PostResponseShortDTO;
import com.example.blogservice.dto.userDTOs.UserResponseFullDTO;
import com.example.blogservice.dto.userDTOs.UserResponseShortDTO;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.BlogPost;
import com.example.blogservice.entity.Tag;
import com.example.blogservice.entity.status.AccountStatus;
import com.example.blogservice.entity.status.PostStatus;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConverterDTO {

    public static PostResponseShortDTO convertPostToDTOShort(BlogPost blogPost) {
        return PostResponseShortDTO.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .build();
    }

    public static PostResponseDTO convertPostToDTO(BlogPost blogPost) {
        return PostResponseDTO.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .body(blogPost.getBody())
                .tagsIds(blogPost.getTags().stream()
                        .map(Tag::getId)
                        .collect(Collectors.toSet()))
                .status(blogPost.getStatus())
                .userId(blogPost.getBlogUser().getId())
                .build();
    }

    public static PostResponseFullDTO convertPostToDTOFull(BlogPost blogPost) {
        return PostResponseFullDTO.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .body(blogPost.getBody())
                .tagsIds(blogPost.getTags().stream()
                        .map(Tag::getId)
                        .collect(Collectors.toSet()))
                .status(blogPost.getStatus())
                .userId(blogPost.getBlogUser().getId())
                .updatedOn(blogPost.getUpdatedOn())
                .createdOn(blogPost.getCreatedOn())
                .build();
    }

    public static BlogPost convertDTOToPost(PostRequestDTO request, BlogUser blogUser, Set<Tag> tags) {
        return BlogPost.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .tags(tags)
                .status(PostStatus.UNPUBLISHED)
                .blogUser(blogUser)
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
    }

    public static UserResponseFullDTO convertUserToDTO(BlogUser blogUser, List<PostResponseShortDTO> postsList) {
        return UserResponseFullDTO.builder()
                .id(blogUser.getId())
                .username(blogUser.getUsername())
                .firstname(blogUser.getFirstName())
                .lastname(blogUser.getLastName())
                .posts(postsList)
                .build();
    }

    public static BlogUser convertDTOToUser(LoginRequestDTO request) {
        return BlogUser.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .accountStatus(AccountStatus.ACTIVE)
                .isAdmin(request.getIsAdmin())
                .roles(
                        request.getIsAdmin()
                                ? new HashSet<>(List.of("admin.posts.ro "))
                                : new HashSet<>(List.of("users.posts.rw"))
                )
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
    }

    public static UserResponseShortDTO convertUserToDTOShort(BlogUser blogUser, Long postsCount) {
        return UserResponseShortDTO.builder()
                .id(blogUser.getId())
                .username(blogUser.getUsername())
                .firstname(blogUser.getFirstName())
                .lastname(blogUser.getLastName())
                .postsCount(postsCount)
                .build();
    }
}