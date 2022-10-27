package com.example.blogservice.dto.loginDTOs;

import com.example.blogservice.entity.BlogUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KeyDTO {
    private String sessionId;
    private BlogUser blogUser;
    private List<String> permissions;
}
