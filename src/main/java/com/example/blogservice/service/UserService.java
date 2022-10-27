package com.example.blogservice.service;

import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.dto.userDTOs.UserResponseFullDTO;
import com.example.blogservice.dto.userDTOs.UserResponseShortDTO;

import java.util.List;

public interface UserService {
    List<UserResponseShortDTO> getAllUsers();
    UserResponseFullDTO getUserById(KeyDTO keyDTO, Long userId);
}
