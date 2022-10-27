package com.example.blogservice.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Entity
@Table(name = "blog_user_session")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlogUserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "session_id", unique = true, nullable = false, updatable = false)
    private String sessionId;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    @OneToOne
    @JoinColumn(name = "blog_user_id", referencedColumnName = "id", nullable = false)
    private BlogUser blogUser;

}
