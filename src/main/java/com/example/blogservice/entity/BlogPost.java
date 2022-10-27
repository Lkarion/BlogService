package com.example.blogservice.entity;

//import com.example.blogservice.entity.converter.TagsStringConverter;
import com.example.blogservice.entity.status.PostStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "blog_post")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlogPost implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @ManyToOne
    @JoinColumn(name = "blog_user_id")
    private BlogUser blogUser;

    @Column(name = "post_status")
    private PostStatus status;

//    @Column(name = "tags")
//    @Convert(converter = TagsStringConverter.class)
//    private String tags;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "created_on")
    private Instant createdOn;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "post_tag",
            joinColumns = {
                    @JoinColumn(name = "blog_post_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "id")
            })
    private Set<Tag> tags = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogPost blogPost = (BlogPost) o;
        return Objects.equals(id, blogPost.id) && Objects.equals(title, blogPost.title) && Objects.equals(body, blogPost.body) && Objects.equals(blogUser, blogPost.blogUser) && status == blogPost.status && Objects.equals(tags, blogPost.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, blogUser, status, tags);
    }
}
