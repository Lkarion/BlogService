package com.example.blogservice.dto.postDTOs;

import com.example.blogservice.entity.status.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostResponseDTO {
    private Long id;
    private String title;
    private String body;
    private Set<Long> tagsIds;
    private PostStatus status;
    private Long userId;
}