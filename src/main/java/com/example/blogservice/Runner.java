//package com.example.blogservice;
//
//import com.example.blogservice.entity.BlogPost;
//import com.example.blogservice.entity.BlogUser;
//import com.example.blogservice.entity.BlogUserPassword;
//import com.example.blogservice.entity.Tag;
//import com.example.blogservice.entity.status.AccountStatus;
//import com.example.blogservice.entity.status.PostStatus;
//import com.example.blogservice.repository.PasswordRepository;
//import com.example.blogservice.repository.PostRepository;
//import com.example.blogservice.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//
//import java.time.Instant;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//@AllArgsConstructor
//public class Runner implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PasswordRepository passwordRepository;
//    private final PostRepository postRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        BlogUser blogUser = BlogUser.builder()
//                .username("JDoe")
//                .firstName("John")
//                .lastName("Doe")
//                .accountStatus(AccountStatus.ACTIVE)
//                .createdOn(Instant.now())
//                .build();
//        userRepository.save(blogUser);
//
//        BlogUserPassword password = BlogUserPassword.builder()
//                .password("pass")
//                .blogUser(blogUser)
//                .build();
//        passwordRepository.save(password);
//
//        Tag tag1 = Tag.builder()
//                .text("tag1")
//                .build();
//        Tag tag2 = Tag.builder()
//                .text("tag2")
//                .build();
//        Tag tag3 = Tag.builder()
//                .text("tag3")
//                .build();
//        Set<Tag> tags = new HashSet<>(Arrays.asList(tag1, tag2, tag3));
//
//        BlogPost blogPost = BlogPost.builder()
//                .title("title")
//                .body("body")
//                .blogUser(blogUser)
//                .status(PostStatus.PUBLISHED)
//                .createdOn(Instant.now())
//                .tags(tags)
//                .build();
//        postRepository.save(blogPost);
//        BCrypt.gensalt();
//    }
//}
