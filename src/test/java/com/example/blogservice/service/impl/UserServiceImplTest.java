package com.example.blogservice.service.impl;

import com.example.blogservice.entity.BlogPost;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.status.AccountStatus;
import com.example.blogservice.entity.status.PostStatus;
import com.example.blogservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    @DisplayName("getAllUsers()")
    class getAllUsers {

        @Test
        @DisplayName("")
        void shouldReturn() {
            BlogUser user1Published = BlogUser.builder()
                    .username("user1Published")
                    .firstName("fn")
                    .lastName("ln")
                    .accountStatus(AccountStatus.ACTIVE)
                    .isAdmin(false)
                    .createdOn(Instant.now())
                    .build();
            BlogPost post1Published = BlogPost.builder()
                    .title("title")
                    .body("body")
                    .blogUser(user1Published)
                    .createdOn(Instant.now())
                    .status(PostStatus.PUBLISHED)
                    .build();
            BlogUser user2Published = BlogUser.builder()
                    .username("user2Published")
                    .firstName("fn")
                    .lastName("ln")
                    .accountStatus(AccountStatus.ACTIVE)
                    .isAdmin(false)
                    .createdOn(Instant.now())
                    .build();
            BlogPost post2Unpublished = BlogPost.builder()
                    .title("title2")
                    .body("body2")
                    .blogUser(user2Published)
                    .createdOn(Instant.now())
                    .status(PostStatus.UNPUBLISHED)
                    .build();
            List<BlogUser> usersWithPublishedPosts = new ArrayList<>(List.of(user1Published));

            Mockito
                    .when(userRepository.findAll())
                    .thenReturn(usersWithPublishedPosts);
            List<BlogUser> result = userRepository.findAll();

            Assertions.assertEquals(usersWithPublishedPosts, result);
        }
    }
}
