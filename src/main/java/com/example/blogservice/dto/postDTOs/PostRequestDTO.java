package com.example.blogservice.dto.postDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostRequestDTO {
    private String title;
    private String body;
    private String tags;
    private Long userId;
}
