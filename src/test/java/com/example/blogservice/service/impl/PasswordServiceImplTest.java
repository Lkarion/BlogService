package com.example.blogservice.service.impl;

import com.example.blogservice.configuration.SessionConfiguration;
import com.example.blogservice.dto.converter.ConverterDTO;
import com.example.blogservice.dto.loginDTOs.LoginRequestDTO;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.status.AccountStatus;
import com.example.blogservice.repository.PasswordRepository;
import com.example.blogservice.repository.SessionRepository;
import com.example.blogservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PasswordServiceImplTest {

    @Mock
    private SessionConfiguration sessionConfiguration;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private PasswordRepository passwordRepository;

    @InjectMocks
    private PasswordServiceImpl passwordService;

    private LoginRequestDTO request;

    @Nested
    @DisplayName("registerUser()")
    class RegisterUser {

        @Test
        void should_Throw_CONFLICT_When_Username_Already_In_DB() {
            request = LoginRequestDTO.builder()
                    .username("Vasya")
                    .password("password")
                    .isAdmin(false)
                    .build();
            HttpStatus expectedStatus = HttpStatus.CONFLICT;
            Mockito
                    .when(userRepository.existsByUsername(request.getUsername()))
                    .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT));

            ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> passwordService.registerUser(request));
            assertEquals(expectedStatus, ex.getStatus());
        }
//        @Test
//        void should_Return_User_When_Request_OK() {
//            request = LoginRequestDTO.builder()
//                    .username("Vasya")
//                    .password("password")
//                    .isAdmin(false)
//                    .build();
//            BlogUser user = BlogUser.builder()
//                .id(1L)
//                .username("Vasya")
//                .firstName("fn")
//                .lastName("ln")
//                .accountStatus(AccountStatus.ACTIVE)
//                .isAdmin(false)
//                .createdOn(Instant.now())
//                .build();
//            Mockito
//                    .when(passwordService.registerUser(request))
//                    .thenReturn(user);
//            HttpStatus expectedStatus = HttpStatus.CONFLICT;
//
//            ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> passwordService.registerUser(request));
//            assertEquals(expectedStatus, ex.getStatus());
//        }
////        BlogUser blogUser = ConverterDTO.convertDTOToUser(request);
////        userRepository.save(blogUser);
////        generateAndSavePassword(blogUser, request.getPassword());
    }
    @Nested
    @DisplayName("login()")
    class Login {

        @Test
        public void should_Throw_UNAUTHORIZED_When_No_Such_Account() {
            request = LoginRequestDTO.builder()
                    .username("Vasya")
                    .password("password")
                    .isAdmin(false)
                    .build();
            HttpStatus expectedStatus = HttpStatus.UNAUTHORIZED;

            Mockito
                    .when(passwordService.login(request))
                    .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            ResponseStatusException ex = assertThrows(
                    ResponseStatusException.class,
                    () -> passwordService.login(request));
            assertEquals(expectedStatus, ex.getStatus());
        }
    }

    @Test
    public void should_Throw_CONFLICT_When_Username_AlreadyInDB3() {
//        BlogUser user = BlogUser.builder()
//                .id(1L)
//                .username("Vasya")
//                .firstName("fn")
//                .lastName("ln")
//                .accountStatus(AccountStatus.ACTIVE)
//                .isAdmin(false)
//                .createdOn(Instant.now())
//                .build();
        request = LoginRequestDTO.builder()
                .username("Vasya")
                .password("password")
                .isAdmin(false)
                .build();
//        Mockito.when(passwordService.login(request))
//                        .then(Optional.of(user));
        Mockito
                .when(userRepository.existsByUsername(request.getUsername()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT));
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> passwordService.registerUser(request));
        assertEquals(expectedStatus, ex.getStatus());
    }

    @Test
    public void should_Throw_CONFLICT_When_Username_AlreadyInDB1() {
//        BlogUser user = BlogUser.builder()
//                .id(1L)
//                .username("Vasya")
//                .firstName("fn")
//                .lastName("ln")
//                .accountStatus(AccountStatus.ACTIVE)
//                .isAdmin(false)
//                .createdOn(Instant.now())
//                .build();
        request = LoginRequestDTO.builder()
                .username("Vasya")
                .password("password")
                .isAdmin(false)
                .build();
//        Mockito.when(passwordService.login(request))
//                        .then(Optional.of(user));
        Mockito
                .when(userRepository.existsByUsername(request.getUsername()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT));
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> passwordService.registerUser(request));
        assertEquals(expectedStatus, ex.getStatus());
    }

    @Test
    public void should_Throw_CONFLICT_When_Username_AlreadyInDB() {
//        BlogUser user = BlogUser.builder()
//                .id(1L)
//                .username("Vasya")
//                .firstName("fn")
//                .lastName("ln")
//                .accountStatus(AccountStatus.ACTIVE)
//                .isAdmin(false)
//                .createdOn(Instant.now())
//                .build();
        request = LoginRequestDTO.builder()
                .username("Vasya")
                .password("password")
                .isAdmin(false)
                .build();
//        Mockito.when(passwordService.login(request))
//                        .then(Optional.of(user));
        Mockito
                .when(userRepository.existsByUsername(request.getUsername()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT));
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> passwordService.registerUser(request));
        assertEquals(expectedStatus, ex.getStatus());
    }

    @Test
    public void should_Throw_CONFLICT_When_Username_AlreadyInDB2() {
//        BlogUser user = BlogUser.builder()
//                .id(1L)
//                .username("Vasya")
//                .firstName("fn")
//                .lastName("ln")
//                .accountStatus(AccountStatus.ACTIVE)
//                .isAdmin(false)
//                .createdOn(Instant.now())
//                .build();
        request = LoginRequestDTO.builder()
                .username("Vasya")
                .password("password")
                .isAdmin(false)
                .build();
//        Mockito.when(passwordService.login(request))
//                        .then(Optional.of(user));
        Mockito
                .when(userRepository.existsByUsername(request.getUsername()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT));
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> passwordService.registerUser(request));
        assertEquals(expectedStatus, ex.getStatus());
    }
}
