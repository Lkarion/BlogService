package com.example.blogservice.service;

import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.dto.userDTOs.UserRolesResponseDTO;
import com.example.blogservice.dto.userDTOs.UserUpdateRequestDTO;

import java.util.List;
import java.util.Set;

public interface AdminService {

    void promoteUser(Long userId);
    void demoteUser(Long userId);
    void updateUserFirstnameOrLastname(UserUpdateRequestDTO request, Long userId);
    List<UserRolesResponseDTO> showUsersToAdmin(Boolean isAdmin);
    Set<String> showUserRolesToAdmin(KeyDTO keyDTO, Long userId);
    void grantUserNewRole(String newRole, Long userId, Boolean resetSession);
    void deleteUserRole(String roleName, Long userId, Boolean resetSession);
    Set<String> showAvailableRoles(KeyDTO keyDTO);
}
