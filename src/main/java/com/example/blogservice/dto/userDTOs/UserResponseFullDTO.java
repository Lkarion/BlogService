package com.example.blogservice.dto.userDTOs;

import com.example.blogservice.dto.postDTOs.PostResponseShortDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponseFullDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private List<PostResponseShortDTO> posts;
}