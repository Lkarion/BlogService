package com.example.blogservice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "blog_user_password")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlogUserPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "salt")
    private String salt;

    @OneToOne
    @JoinColumn(name = "blog_user_id", referencedColumnName = "id", unique = true, nullable = false)
    private BlogUser blogUser;
}
