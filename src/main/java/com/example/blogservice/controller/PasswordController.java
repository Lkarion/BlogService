package com.example.blogservice.controller;

import com.example.blogservice.dto.loginDTOs.LoginRequestDTO;
import com.example.blogservice.dto.loginDTOs.LoginResponseDTO;
import com.example.blogservice.dto.loginDTOs.PasswordUpdateRequestDTO;
import com.example.blogservice.dto.loginDTOs.KeyDTO;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    /**
     * TODO
     * Creates BlogUser with roles
     * users.posts.rw
     */
    @PostMapping("/entry/registration")
    public void registerUser(@RequestBody LoginRequestDTO request) {
        passwordService.registerUser(request);
    }

    /**
     * TODO
     * Response { sessionId: token }
     */
    @PostMapping("/entry/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        return passwordService.login(request);
    }

    /**
     * TODO
     * Deprecate/Expire session token
     */
    @PutMapping("/entry/logout")
    public void logout(@AuthenticationPrincipal KeyDTO request) {
        passwordService.logout(request);
    }

    @GetMapping("/accounts/me")
    public BlogUser getMyAccount(@AuthenticationPrincipal KeyDTO session) {
        return passwordService.getMyAccount(session);
    }

    @PutMapping("/entry/update-password")
    public void updatePassword(@AuthenticationPrincipal KeyDTO keyDTO,
                               @RequestBody PasswordUpdateRequestDTO request) {
        passwordService.updatePassword(keyDTO, request);
    }

}
