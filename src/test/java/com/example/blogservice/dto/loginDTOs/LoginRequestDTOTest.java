package com.example.blogservice.dto.loginDTOs;

import com.example.blogservice.configuration.SessionConfiguration;
import com.example.blogservice.entity.BlogUser;
import com.example.blogservice.entity.status.AccountStatus;
import com.example.blogservice.repository.PasswordRepository;
import com.example.blogservice.repository.SessionRepository;
import com.example.blogservice.repository.UserRepository;
import com.example.blogservice.service.impl.PasswordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LoginRequestDTOTest {

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

    @Test
    public void should_Throw_Exception_When_Username_Null() {
        BlogUser user = BlogUser.builder()
                .username("username")
                .firstName("fn")
                .lastName("ln")
                .createdOn(Instant.now())
                .isAdmin(false)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
        request = LoginRequestDTO.builder()
                .username(null)
                .password("password")
                .isAdmin(true)
                .build();
        Mockito
                .when(passwordService.login(request))
                .thenThrow(new IllegalArgumentException());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> passwordService.login(request));

        String expectedMessage = "Username should not be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void should_Throw_Exception_When_Username_Blank() {
        request = LoginRequestDTO.builder()
                .username("")
                .password("password")
                .isAdmin(true)
                .build();
        Mockito
                .when(passwordService.login(request))
                .thenThrow(new IllegalArgumentException());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> passwordService.login(request));

        String expectedMessage = "Username should not be blank";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void should_Throw_Exception_When_Password_Null() {
        request = LoginRequestDTO.builder()
                .username("username")
                .password(null)
                .isAdmin(true)
                .build();
        Mockito
                .when(passwordService.login(request))
                .thenThrow(new IllegalArgumentException());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> passwordService.login(request));

        String expectedMessage = "Password should not be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void should_Throw_Exception_When_Password_LengthLess() {
        request = LoginRequestDTO.builder()
                .username("username")
                .password("pass")
                .isAdmin(true)
                .build();
        Mockito
                .when(passwordService.login(request))
                .thenThrow(new IllegalArgumentException());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> passwordService.login(request));

        String expectedMessage = "Password should contain from 8 to 25 signs";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void should_Throw_Exception_When_Password_LengthMore() {
        request = LoginRequestDTO.builder()
                .username("username")
                .password("passqqqwdcwdwedddddsvdwdlvmwnvwnvwndvnwnvdpwnpddd")
                .isAdmin(true)
                .build();
        Mockito
                .when(passwordService.login(request))
                .thenThrow(new IllegalArgumentException());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> passwordService.login(request));

        String expectedMessage = "Password should contain from 8 to 25 signs";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void should_Throw_Exception_When_isAdmin_Null() {
        request = LoginRequestDTO.builder()
                .username("username")
                .password("password")
                .isAdmin(null)
                .build();
        Mockito
                .when(passwordService.login(request))
                .thenThrow(new IllegalArgumentException());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> passwordService.login(request));

        String expectedMessage = "isAdmin should not be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
