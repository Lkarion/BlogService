package com.example.blogservice.dto.converter;

import com.example.blogservice.dto.loginDTOs.LoginRequestDTO;
import com.example.blogservice.dto.postDTOs.PostRequestDTO;
import com.example.blogservice.dto.postDTOs.PostResponseDTO;
import com.example.blogservice.dto.postDTOs.PostResponseFullDTO;
import com.example.blogservice.dto.postDTOs.PostResponseShortDTO;
import com.example.blogservice.dto.userDTOs.UserResponseFullDTO;
import com.example.blogservice.dto.userDTOs.UserResponseShortDTO;
import com.example.blogservice.entity.BlogPost;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.Tag;
import com.example.blogservice.entity.status.AccountStatus;
import com.example.blogservice.entity.status.PostStatus;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

public class ConverterDTOTest {

    private final ConverterDTO converterDTO = new ConverterDTO();

    @Nested
    @DisplayName("convert BlogPost to DTOShort")
    class ConvertPostToDTOShort {

        @Test
        @DisplayName("should return postDTOShort when called with blogPost")
        void shouldReturnPostDTOShortWhenCalledWithBlogPost() {

            BlogPost post1 = BlogPost.builder()
                    .id(1L)
                    .title("title")
                    .build();
            PostResponseShortDTO post1DTO = PostResponseShortDTO.builder()
                    .id(1L)
                    .title("title")
                    .build();
            var result = converterDTO.convertPostToDTOShort(post1);

            Assertions.assertEquals(post1DTO, result);
        }
    }

    @Nested
    @DisplayName("convert BlogPost to DTO")
    class ConvertPostToDTO {

        @Test
        @DisplayName("should return postDTO when called with blogPost")
        void shouldReturnPostDTOWhenCalledWithBlogPost() {

            BlogUser user = new BlogUser(1L, "username","fn", "ln", AccountStatus.ACTIVE, false, Instant.now(), Instant.now(), new HashSet<>(), null, null);
            BlogPost post1 = BlogPost.builder()
                    .id(1L)
                    .title("title")
                    .body("body")
                    .tags(new HashSet<>())
                    .status(PostStatus.PUBLISHED)
                    .blogUser(user)
                    .build();
            PostResponseDTO post1DTO = PostResponseDTO.builder()
                    .id(1L)
                    .title("title")
                    .body("body")
                    .tagsIds(new HashSet<>())
                    .status(PostStatus.PUBLISHED)
                    .userId(1L)
                    .build();
            var result = converterDTO.convertPostToDTO(post1);

            Assertions.assertEquals(post1DTO, result);
        }
    }

    @Nested
    @DisplayName("convert BlogPost to DTOFull")
    class ConvertPostToDTOFull {

        @Test
        @DisplayName("should return postFullDTO when called with blogPost")
        void shouldReturnPostDTOFullWhenCalledWithBlogPost() {

            BlogUser user = new BlogUser(1L, "username","fn", "ln", AccountStatus.ACTIVE, false, Instant.now(), Instant.now(), new HashSet<>(), null, null);
            var createdOn = Instant.now();
            var updatedOn = Instant.now();

            BlogPost post1 = BlogPost.builder()
                    .id(1L)
                    .title("title")
                    .body("body")
                    .tags(new HashSet<>())
                    .status(PostStatus.PUBLISHED)
                    .blogUser(user)
                    .createdOn(createdOn)
                    .updatedOn(updatedOn)
                    .build();
            PostResponseFullDTO post1DTO = PostResponseFullDTO.builder()
                    .id(1L)
                    .title("title")
                    .body("body")
                    .tagsIds(new HashSet<>())
                    .status(PostStatus.PUBLISHED)
                    .userId(user.getId())
                    .updatedOn(updatedOn)
                    .createdOn(createdOn)
                    .build();
            var result = converterDTO.convertPostToDTOFull(post1);

            Assertions.assertEquals(post1DTO, result);
        }
    }

    @Nested
    @DisplayName("convert DTO To BlogPost")
    class ConvertDTOToPost {

        @Test
        @DisplayName("should return postFullDTO when called with blogPost")
        void shouldReturnBlogPostWhenCalledWithPostRequestDTO() {

            BlogUser user = new BlogUser(1L, "username","fn", "ln",
                    AccountStatus.ACTIVE, false, Instant.now(), Instant.now(),
                    new HashSet<>(), null, null);

            var tags = new HashSet<>(List.of(new Tag("tag1")));
            PostRequestDTO post1DTO = PostRequestDTO.builder()
                    .title("title")
                    .body("body")
                    .tags("tag1")
                    .userId(user.getId())
                    .build();
            BlogPost post1 = BlogPost.builder()
                    .title("title")
                    .body("body")
                    .tags(tags)
                    .status(PostStatus.UNPUBLISHED)
                    .blogUser(user)
                    .createdOn(Instant.now())
                    .updatedOn(Instant.now())
                    .build();
            var result = converterDTO.convertDTOToPost(post1DTO, user, tags);

            Assertions.assertEquals(post1, result);
        }
    }

    @Nested
    @DisplayName("convert BlogUser To DTO")
    class convertUserToDTO {

        @Test
        @DisplayName("should return userDTO when called with blogUser")
        void shouldReturnUserDTOWhenCalledWithBlogUser() {

            BlogUser user = new BlogUser(1L, "username","fn", "ln",
                    AccountStatus.ACTIVE, false, Instant.now(), Instant.now(),
                    new HashSet<>(), null, null);

            PostResponseShortDTO postDTO = PostResponseShortDTO.builder()
                    .id(1L)
                    .title("title")
                    .status(PostStatus.PUBLISHED)
                    .build();
            var userDTO = UserResponseFullDTO.builder()
                    .id(1L)
                    .username("username")
                    .firstname("fn")
                    .lastname("ln")
                    .posts(List.of(postDTO))
                    .build();
            var result = converterDTO.convertUserToDTO(user, List.of(postDTO));

            Assertions.assertEquals(userDTO, result);
        }
    }

    @Nested
    @DisplayName("convert DTO To BlogUser")
    class ConvertDTOToUser {

        @Test
        @DisplayName("should return BlogUser when called with LoginRequestDTO")
        void shouldReturnBlogUserWhenCalledWithLoginRequestDTO() {

            var tags = new HashSet<>(List.of(new Tag("tag1")));

            LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                    .username("username")
                    .firstName("fn")
                    .lastName("ln")
                    .password("password")
                    .isAdmin(false)
                    .build();
            var user = BlogUser.builder()
                    .username("username")
                    .firstName("fn")
                    .lastName("ln")
                    .accountStatus(AccountStatus.ACTIVE)
                    .isAdmin(false)
                    .roles(new HashSet<>(List.of("users.posts.rw")))
                    .createdOn(Instant.now())
                    .updatedOn(Instant.now())
                    .build();
            var result = converterDTO.convertDTOToUser(requestDTO);

            Assertions.assertEquals(user.getUsername(), result.getUsername());
        }
    }

    @Nested
    @DisplayName("convert BlogUser To DTOShort")
    class ConvertUserToDTOShort {

        @Test
        @DisplayName("should return DTOShort when called with blogUser")
        void shouldReturnDTOShortWhenCalledWithBlogUser() {

            BlogUser user = new BlogUser(1L, "username","fn", "ln",
                    AccountStatus.ACTIVE, false, Instant.now(), Instant.now(),
                    new HashSet<>(), null, null);

            var userDTO = UserResponseShortDTO.builder()
                    .id(1L)
                    .username("username")
                    .firstname("fn")
                    .lastname("ln")
                    .postsCount(0L)
                    .build();
            var result = converterDTO.convertUserToDTOShort(user, 0L);

            Assertions.assertEquals(userDTO, result);
        }
    }

}
