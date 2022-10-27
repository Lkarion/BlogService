package com.example.blogservice.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tag implements Comparable<Tag>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text"/*, unique = true*/)//TODO
    private String text;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<BlogPost> posts = new HashSet<>();

    @Override
    public int compareTo(Tag o) {
        return (int) (this.id - o.getId());
    }

    public Tag(String text) {
        this.text = text;
        this.posts = new HashSet<>();
    }
}
