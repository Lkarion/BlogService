package com.example.blogservice.controller;

import com.example.blogservice.dto.loginDTOs.PasswordResetRequestDTO;
import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.dto.userDTOs.UserRolesResponseDTO;
import com.example.blogservice.dto.userDTOs.UserUpdateRequestDTO;
import com.example.blogservice.service.AdminService;
import com.example.blogservice.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final PasswordService passwordService;

    @PreAuthorize("hasAuthority('admin.admins.rw')")
    @PutMapping("/admin/users/{userId}/promote")
    public void promoteUser(@PathVariable(name = "userId") Long userId) {
        adminService.promoteUser(userId);
    }

    @PreAuthorize("hasAuthority('admin.admins.rw')")
    @PutMapping("/admin/users/{userId}/demote")
    public void demoteUser(@PathVariable(name = "userId") Long userId) {
        adminService.demoteUser(userId);
    }

    @PreAuthorize("hasAuthority('admin.users.rw')")
    @PatchMapping("/admin/users/{userId}")
    public void updateUserFirstnameOrLastname(@RequestBody UserUpdateRequestDTO request,
                                              @PathVariable(name = "userId") Long userId) {
        adminService.updateUserFirstnameOrLastname(request, userId);
    }

    @PreAuthorize("hasAuthority('admin.users.rw')")
    @PutMapping("/admin/users/{userId}/password?reset-session=true")
    public void resetPasswordByUserId(@AuthenticationPrincipal KeyDTO keyDTO,
                                      @RequestBody PasswordResetRequestDTO request,
                                      @PathVariable("userId") Long userId,
                                      @RequestParam(value = "reset-session", required = false) Boolean resetSession) {
        passwordService.resetPasswordByUserId(keyDTO, request, userId, resetSession);
    }

    @GetMapping("/admin/users?admin=true")
    public List<UserRolesResponseDTO> showUsersToAdmin(@RequestParam(value = "admin") Boolean isAdmin) {
        return adminService.showUsersToAdmin(isAdmin);
    }

    @PreAuthorize("hasAuthority('admin.roles.ro')")
    @GetMapping("/admin/users/{userId}/roles")
    public Set<String> showUserRolesToAdmin(@AuthenticationPrincipal KeyDTO keyDTO,
                                            @PathVariable(name = "userId") Long userId) {
        return adminService.showUserRolesToAdmin(keyDTO, userId);
    }

    @PreAuthorize("hasAuthority('admin.roles.rw')")
    @PutMapping("/admin/users/{userId}/roles?reset-session=true")
    public void grantUserNewRole(@RequestBody String newRole,
                                 @PathVariable("userId") Long userId,
                                 @RequestParam(value = "reset-session", required = false) Boolean resetSession) {
        adminService.grantUserNewRole(newRole, userId, resetSession);
    }

    @PreAuthorize("hasAuthority('admin.roles.rw')")
    @DeleteMapping("/admin/users/{userId}/roles?reset-session=true")
    public void deleteUserRole(@RequestBody String roleName,
                               @PathVariable("userId") Long userId,
                               @RequestParam(value = "reset-session", required = false) Boolean resetSession) {
        adminService.deleteUserRole(roleName, userId, resetSession);
    }

    @PreAuthorize("hasAuthority('admin.roles.ro')")
    @GetMapping("/admin/roles")
    public Set<String> showAvailableRoles(@AuthenticationPrincipal KeyDTO keyDTO) {
        return adminService.showAvailableRoles(keyDTO);
    }
}
