package com.example.blogservice.service.impl;

import com.example.blogservice.dto.converter.ConverterDTO;
import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.dto.postDTOs.PostResponseShortDTO;
import com.example.blogservice.dto.userDTOs.UserResponseShortDTO;
import com.example.blogservice.entity.BlogPost;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.Tag;
import com.example.blogservice.entity.status.AccountStatus;
import com.example.blogservice.entity.status.PostStatus;
import com.example.blogservice.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private ConverterDTO converterDTO;
    @InjectMocks
    private PostServiceImpl postService;

    static BlogUser user;
    static Set<Tag> tags;
    static BlogPost post1;
    static PostResponseShortDTO post1ShortDTO;
    static BlogPost post2;
    static PostResponseShortDTO post2ShortDTO;
    static UserResponseShortDTO userShortDTO;
    static KeyDTO keyDTOAdmin;
    static KeyDTO keyDTOUser;

    @BeforeAll
    static void init() {
        user = new BlogUser(1L, "username","fn", "ln",
                AccountStatus.ACTIVE, false, Instant.now(), Instant.now(),
                new HashSet<>(), null, null);

        tags = new HashSet<>(List.of(new Tag("tag1")));

        post1 = BlogPost.builder()
                .title("title")
                .body("body")
                .tags(tags)
                .status(PostStatus.UNPUBLISHED)
                .blogUser(user)
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
        post1ShortDTO = PostResponseShortDTO.builder()
                .id(1L)
                .title("title")
                .build();
        post2 = BlogPost.builder()
                .title("title2")
                .body("body")
                .tags(tags)
                .status(PostStatus.PUBLISHED)
                .blogUser(user)
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
        post2ShortDTO = PostResponseShortDTO.builder()
                .id(2L)
                .title("title2")
                .build();
        userShortDTO = UserResponseShortDTO.builder()
                .id(1L)
                .username("username")
                .firstname("fn")
                .lastname("ln")
                .postsCount(0L)
                .build();
        keyDTOAdmin = KeyDTO.builder()
                .sessionId("adminSession")
                .permissions(new ArrayList<>(List.of("admin.posts.ro")))
                .build();
        keyDTOUser = KeyDTO.builder()
                .sessionId("userSession")
                .permissions(new ArrayList<>(List.of("users.posts.rw")))
                .build();
    }

    @Nested
    @DisplayName("Get All Blog Posts")
    class getAllBlogPosts {

        @Test
        public void should_Return_All_Blogs_When_Admin() {
            var resultList = new ArrayList<>(List.of(post1ShortDTO, post2ShortDTO));
            Mockito
                    .when(postService.getAllBlogPosts(keyDTOAdmin))
                    .thenReturn(resultList);
            List<PostResponseShortDTO> result = postService.getAllBlogPosts(keyDTOAdmin);

            Assertions.assertEquals(resultList, result);
        }
    }
}
