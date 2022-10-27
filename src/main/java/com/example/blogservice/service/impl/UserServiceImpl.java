package com.example.blogservice.service.impl;

import com.example.blogservice.dto.converter.ConverterDTO;
import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.dto.postDTOs.PostResponseShortDTO;
import com.example.blogservice.dto.userDTOs.UserResponseFullDTO;
import com.example.blogservice.dto.userDTOs.UserResponseShortDTO;
import com.example.blogservice.entity.BlogPost;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.status.PostStatus;
import com.example.blogservice.repository.PostRepository;
import com.example.blogservice.repository.SessionRepository;
import com.example.blogservice.repository.UserRepository;
import com.example.blogservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SessionRepository sessionRepository;
//    private final ConverterDTO converterDTO;

    /**
     * Shows authors, order by articles count (desc)
     * Counts articles with status PUBLISHED only
     */
    public List<UserResponseShortDTO> getAllUsers() {
        var users = userRepository.findAll();
        List<UserResponseShortDTO> result = new ArrayList<>();
        for (BlogUser blogUser : users) {
            result.add(ConverterDTO.convertUserToDTOShort(
                            blogUser,
                            postRepository.countByBlogUserAndStatus(blogUser, PostStatus.PUBLISHED)
                    )
            );
        }
        return sortByArticlesCountDESC(result);
    }

    private List<UserResponseShortDTO> sortByArticlesCountDESC(List<UserResponseShortDTO> list) {
        return list.stream()
                .sorted(
                        Comparator.comparingLong(UserResponseShortDTO::getPostsCount)
                                .reversed()
                )
                .toList();
    }

    /**
     * Shows author and their articles (order by created date desc)
     * Show articles with status PUBLISHED only
     * Show all blog posts to the author or to an admin user with role
     * admin.posts.ro
     */
    public UserResponseFullDTO getUserById(KeyDTO keyDTO, Long userId) {
        var userDB = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var session = sessionRepository.findBySessionId(keyDTO.getSessionId());
        var userInSession = session.getBlogUser();

        List<BlogPost> postsDB;
        if (keyDTO.getPermissions().contains("admin.posts.ro")
                || userDB.equals(userInSession)) {
            postsDB = postRepository.findAllByBlogUser(userDB);
        } else {
            postsDB = postRepository.findAllByBlogUserAndStatus(userDB, PostStatus.PUBLISHED);
        }
        var posts = getConvertedPosts(postsDB);
        return ConverterDTO.convertUserToDTO(userDB, posts);
    }

    private List<PostResponseShortDTO> getConvertedPosts(List<BlogPost> postsDB) {
        return postsDB.stream()
                .sorted(Comparator.comparing(BlogPost::getCreatedOn)
                        .reversed())
                .map(ConverterDTO::convertPostToDTOShort)
                .toList();
    }
}
