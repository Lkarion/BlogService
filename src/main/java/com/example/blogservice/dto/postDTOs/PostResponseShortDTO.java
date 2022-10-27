package com.example.blogservice.dto.postDTOs;

import com.example.blogservice.entity.status.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostResponseShortDTO {

    private Long id;
    private String title;
    private PostStatus status;
}
