package com.example.blogservice.service.impl;

import com.example.blogservice.configuration.SessionConfiguration;
import com.example.blogservice.dto.converter.ConverterDTO;
import com.example.blogservice.dto.loginDTOs.*;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.BlogUserPassword;
import com.example.blogservice.entity.BlogUserSession;
import com.example.blogservice.repository.PasswordRepository;
import com.example.blogservice.repository.SessionRepository;
import com.example.blogservice.repository.UserRepository;
import com.example.blogservice.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final SessionConfiguration sessionConfiguration;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordRepository passwordRepository;

    public void generateAndSavePassword(BlogUser blogUser, String password) {
        String salt = BCrypt.gensalt();
        String encryptedPassword = BCrypt.hashpw(password, salt);

        BlogUserPassword accountPassword = BlogUserPassword.builder()
                .blogUser(blogUser)
                .salt(salt)
                .passwordHash(encryptedPassword)
                .build();

        passwordRepository.save(accountPassword);
    }

    public BlogUser getMatchedAccount(String username, String password) {
        BlogUser blogUser = userRepository.findByUsername(username);

        if (blogUser == null) {
            return null;
        }
        BlogUserPassword accountPassword = passwordRepository.findByBlogUser(blogUser);

        var actualPasswordHash = BCrypt.hashpw(password, accountPassword.getSalt());

        return actualPasswordHash.equals(accountPassword.getPasswordHash()) ? blogUser : null;
    }

    public void registerUser(LoginRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        BlogUser blogUser = ConverterDTO.convertDTOToUser(request);
        userRepository.save(blogUser);
        generateAndSavePassword(blogUser, request.getPassword());
    }

    public LoginResponseDTO login(LoginRequestDTO request) { //TODO should return JWTTocken!
        BlogUser account = getMatchedAccount(request.getUsername(), request.getPassword());
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        BlogUserSession session = BlogUserSession.builder()
                .blogUser(account)
                .sessionId(UUID.randomUUID().toString())
                .build();
        session.setExpirationTime(LocalDateTime.now().plusMinutes(sessionConfiguration.getExpirationPeriod()));

        sessionRepository.save(session);

        return LoginResponseDTO.builder()
                .sessionId(session.getSessionId())
                .build();
    }

    public void logout(KeyDTO request) {
        sessionRepository.deleteBySessionId(request.getSessionId());
    }

    public BlogUser getMyAccount(KeyDTO session) {
        return sessionRepository.findBySessionId(session.getSessionId())
                .getBlogUser();
    }

    public void resetPasswordByUserId(KeyDTO keyDTO, PasswordResetRequestDTO request, Long userId, Boolean resetSession) {
        var userDB = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var password = passwordRepository.findByBlogUser(userDB);
        if (password == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String encryptedPassword = BCrypt.hashpw(request.getPassword(), password.getSalt());
        password.setPasswordHash(encryptedPassword);
        passwordRepository.save(password);

        if (resetSession) {
            var sessions = sessionRepository.findAllByBlogUser(userDB);
            sessionRepository.deleteAll(sessions);
        }
    }

    public void updatePassword(KeyDTO keyDTO, PasswordUpdateRequestDTO request) {
        var session = sessionRepository.findBySessionId(keyDTO.getSessionId());
        var userDB = session.getBlogUser();
        var userRequested = getMatchedAccount(request.getUsername(), request.getOldPassword());
        if (!userDB.equals(userRequested)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT); //TODO another status
        }
        var password = passwordRepository.findByBlogUser(userDB);
        String encryptedPassword = BCrypt.hashpw(request.getNewPassword(), password.getSalt());
        password.setPasswordHash(encryptedPassword);
        passwordRepository.save(password);
    }
}
