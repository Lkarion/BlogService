package com.example.blogservice.dto.userDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponseShortDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private Long postsCount;
}
