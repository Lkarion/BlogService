package com.example.blogservice.service.impl;

import com.example.blogservice.dto.converter.ConverterDTO;
import com.example.blogservice.dto.converter.ConverterDTOList;
import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.dto.postDTOs.*;
import com.example.blogservice.entity.BlogPost;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.Tag;
import com.example.blogservice.entity.status.PostStatus;
import com.example.blogservice.repository.PostRepository;
import com.example.blogservice.repository.TagRepository;
import com.example.blogservice.repository.UserRepository;
import com.example.blogservice.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    /**
     * Search last posts, order by createdOn desc //TODO 30 latest z.B.
     * Shows only posts with status PUBLISHED
     * For admin user with role
     * admin.posts.ro - show PUBLISHED, UNPUBLISHED and BLOCKED
     */
    public List<PostResponseShortDTO> getAllBlogPosts(KeyDTO keyDTO) {
        List<BlogPost> postsDB;
        if (keyDTO.getPermissions().contains("admin.posts.ro")) {
            postsDB = postRepository.findAll();
        } else {
            postsDB = postRepository.findAllByStatus(PostStatus.PUBLISHED);
        }
        return getSortedAndConvertedPosts(postsDB);
    }

    private List<PostResponseShortDTO> getSortedAndConvertedPosts(List<BlogPost> postsDB) {
        return postsDB.stream()
                .sorted(Comparator.comparing(BlogPost::getCreatedOn)
                        .reversed())
                .map(ConverterDTO::convertPostToDTOShort)
                .toList();
    }

    public PostResponseDTO createBlogPost(PostRequestDTO request) {
        var tags = convertStringToTagsSet(request.getTags().split(","));
        tags.stream()
                .filter(tag -> !tagRepository.existsByText(tag.getText()))
                .forEach(tagRepository::save);

        BlogUser blogUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        BlogPost blogPost = ConverterDTO.convertDTOToPost(request, blogUser, tags);
        postRepository.save(blogPost);

        updateTags(tags, blogPost);
        return ConverterDTO.convertPostToDTO(blogPost);
    }

    private Set<Tag> convertStringToTagsSet(String... strings) {
        Set<Tag> tags = new HashSet<>();
        for (String s : strings) {
            Tag tag = Tag.builder()
                    .text(s.toLowerCase())
                    .posts(new HashSet<>())
                    .build();
            tags.add(tag);
        }
        return tags;
    }

    /**
     * TODO
     * Searches posts by search request
     * Shows only posts with status PUBLISHED
     * For admin user with role admin.posts.ro - show PUBLISHED, UNPUBLISHED and BLOCKED
     */
    public List<PostResponseShortDTO> findBlogPosts(PostSearchRequestDTO request) {
        var posts = postRepository.findAllByStatus(PostStatus.PUBLISHED);

        Set<Tag> tagsInRequest = convertStringToTagsSet(request.getTags());

        return posts.stream()
                .filter(post ->
                        post.getTitle().equalsIgnoreCase(request.getTitle())
                                && post.getBlogUser().getFirstName().equalsIgnoreCase(request.getUserFirstName())
                                && post.getBlogUser().getLastName().equalsIgnoreCase(request.getUserLastName())
                                && post.getTags().equals(tagsInRequest)
                                && post.getCreatedOn().isAfter(request.getPublishedFrom())
                                && post.getCreatedOn().isBefore(request.getPublishedTo())
                )
                .map(ConverterDTO::convertPostToDTOShort)
                .toList();
    }

    /**
     * Shows the entire blog article
     * If the status is PUBLISHED - show always
     * If the status is UNPUBLISHED or BLOCKED - show to the author or to an admin user with role admin.posts.ro
     */
    public PostResponseFullDTO getBlogPostById(KeyDTO keyDTO, Long postId) {
        var blogPost = getPostByIdFromDB(postId);
        if (blogPost.getStatus().equals(PostStatus.PUBLISHED)) {
            return ConverterDTO.convertPostToDTOFull(blogPost);
        }
        var user = keyDTO.getBlogUser();
        if (user.equals(blogPost.getBlogUser())
                || keyDTO.getPermissions().contains("admin.posts.ro")) {
            return ConverterDTO.convertPostToDTOFull(blogPost);
        }
        throw new AuthorizationServiceException("You are not eligible to see this blogPost");
//TODO AuthorizationServiceException everywhere
    }

    /**
     * Updating the existing article
     * Only the author can call this API or admin with role admin.posts.rw
     */
    public void updateBlogPost(KeyDTO keyDTO, Long postId, PostRequestDTO request) {
        var blogPost = getPostByIdFromDB(postId);
        if (!keyDTO.getPermissions().contains("admin.posts.rw")
        || !keyDTO.getBlogUser().equals(blogPost.getBlogUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        var tags = convertStringToTagsSet(request.getTags().split(","));
        tags.stream()
                .filter(tag -> !tagRepository.existsByText(tag.getText()))
                .forEach(tagRepository::save);

        blogPost.setTitle(request.getTitle());
        blogPost.setBody(request.getBody());
        blogPost.setTags(tags);
        blogPost.setUpdatedOn(Instant.now());
        postRepository.save(blogPost);

        updateTags(tags, blogPost);
    }

    private void updateTags(Set<Tag> tags, BlogPost blogPost) {
        tags.forEach(tag -> {
            tag.getPosts().add(blogPost); // TODO implement delete blogPost from tag.getPosts!
            tagRepository.save(tag);
        });
    }

    /**
     * Only the author can call this API and only with role users.posts.rw
     */
    public void publishBlogPostById(KeyDTO keyDTO, Long postId) {
        var blogPost = getPostByIdFromDB(postId);
        if (!keyDTO.getBlogUser().equals(blogPost.getBlogUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        blogPost.setStatus(PostStatus.PUBLISHED);
        blogPost.setUpdatedOn(Instant.now());
        postRepository.save(blogPost);
    }

    /**
     * The author can call this API, regardless of the role set.
     */
    public void unpublishBlogPostById(KeyDTO keyDTO, Long postId) {
        var blogPost = getPostByIdFromDB(postId);
        if (!keyDTO.getBlogUser().equals(blogPost.getBlogUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        blogPost.setStatus(PostStatus.UNPUBLISHED);
        blogPost.setUpdatedOn(Instant.now());
        postRepository.save(blogPost);
    }

    /**
     * Admin users can call this API with role admin.posts.rw
     */
    public void toggleBlockOrPublishBlogPostById(Long postId) {
        var blogPost = getPostByIdFromDB(postId);
        if (blogPost.getStatus().equals(PostStatus.UNPUBLISHED)) {
            throw new IllegalArgumentException("Impossible to toggle an UNPUBLISHED blogPost");
        }
        if (blogPost.getStatus().equals(PostStatus.PUBLISHED)) {
            blogPost.setStatus(PostStatus.BLOCKED);
        } else {
            blogPost.setStatus(PostStatus.PUBLISHED);
        }
        blogPost.setUpdatedOn(Instant.now());
        postRepository.save(blogPost);
    }

    /**
     * Shows blog articles by a specific user
     * Show all posts with the status  PUBLISHED
     * If the status is UNPUBLISHED or BLOCKED - show to the author or to an admin user with role
     * admin.posts.ro
     */
    public List<PostResponseShortDTO> getBlogPostsByUsername(KeyDTO keyDTO, String username) {
        var user = keyDTO.getBlogUser();
        List<BlogPost> posts;
        if (keyDTO.getPermissions().contains("admin.posts.ro")
                || user.getUsername().equals(username)) {
            posts = postRepository.findAllByBlogUser(user);
        } else {
            posts = postRepository.findAllByBlogUserAndStatus(user, PostStatus.PUBLISHED);
        }
        return ConverterDTOList.convertPostsListToDTOShort(posts);
    }

    /**
     * The author can call this API, regardless of the role set.
     */
    public void deleteBlogPostById(KeyDTO keyDTO, Long postId) {
        var blogPost = getPostByIdFromDB(postId);
        if (!keyDTO.getBlogUser().equals(blogPost.getBlogUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        tagRepository.findAllByPostsContaining(blogPost)
                .forEach(tag -> tag.getPosts().remove(blogPost));
        postRepository.deleteById(postId);
    }

    private BlogPost getPostByIdFromDB(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}