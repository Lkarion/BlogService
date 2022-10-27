package com.example.blogservice.service;

import com.example.blogservice.dto.loginDTOs.*;
import com.example.blogservice.entity.BlogUser;

public interface PasswordService {
    void generateAndSavePassword(BlogUser blogUser, String password);
    BlogUser getMatchedAccount(String username, String password);
    void registerUser(LoginRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);
    void logout(KeyDTO request);
    BlogUser getMyAccount(KeyDTO session);
    void updatePassword(KeyDTO keyDTO, PasswordUpdateRequestDTO request);
    void resetPasswordByUserId(KeyDTO keyDTO, PasswordResetRequestDTO request, Long userId, Boolean resetSession);

}
