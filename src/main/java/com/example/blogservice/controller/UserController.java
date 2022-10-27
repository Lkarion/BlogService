package com.example.blogservice.controller;

import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.dto.userDTOs.UserResponseFullDTO;
import com.example.blogservice.dto.userDTOs.UserResponseShortDTO;
import com.example.blogservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<UserResponseShortDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserResponseFullDTO getUserById(@AuthenticationPrincipal KeyDTO keyDTO,
                                           @PathVariable(name = "id") Long userId) {
        return userService.getUserById(keyDTO, userId);
    }

}
