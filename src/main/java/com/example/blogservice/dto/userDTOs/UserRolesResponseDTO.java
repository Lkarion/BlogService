package com.example.blogservice.dto.userDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRolesResponseDTO {
    private Long userId;
    private String name;
    private String username;
    private Boolean isAdmin;
    private Boolean isROAdmin;
    private Set<String> roles;
}
