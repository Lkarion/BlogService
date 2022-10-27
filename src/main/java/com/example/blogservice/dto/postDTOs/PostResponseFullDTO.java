package com.example.blogservice.dto.postDTOs;

import com.example.blogservice.entity.status.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class PostResponseFullDTO {
    private Long id;
    private String title;
    private String body;
    private Set<Long> tagsIds;
    private PostStatus status;
    private Long userId;
    private Instant updatedOn;
    private Instant createdOn;
}
