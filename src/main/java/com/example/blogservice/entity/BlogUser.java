package com.example.blogservice.entity;

import com.example.blogservice.entity.converter.RolesStringConverter;
import com.example.blogservice.entity.status.AccountStatus;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "blog_user", schema = "public")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlogUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatus accountStatus;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "roles")
    @Convert(converter = RolesStringConverter.class)
    private Set<String> roles;

    @ManyToOne
    @JoinColumn(name = "updated_by_blog_user_id")
    private BlogUser updatedBy;

    @ManyToOne
    @JoinColumn(name = "created_by_blog_user_id")
    private BlogUser createdBy;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogUser blogUser = (BlogUser) o;
        return id.equals(blogUser.id) && username.equals(blogUser.username) && createdOn.equals(blogUser.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, createdOn);
    }

//    public static void main(String[] args) {
//        var roles = new HashSet<>(List.of("ad.d","asd.ro"));
//        var user = BlogUser.builder()
//                .accountStatus(AccountStatus.ACTIVE)
//                .isAdmin(true)
//                .createdOn(Instant.now())
//                .lastName("d")
//                .firstName("dvvs")
//                .updatedOn(Instant.now())
//                .username("uashdfa")
//                .roles(roles)
//                .build();
//    }
}
