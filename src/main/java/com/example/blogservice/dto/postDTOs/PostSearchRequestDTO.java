package com.example.blogservice.dto.postDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostSearchRequestDTO {
    private String title;
    private String userFirstName;
    private String userLastName;
    private Instant publishedFrom;
    private Instant publishedTo;
    private String tags;
}
