package com.example.blogservice.dto.loginDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginRequestDTO {

    @NotNull(message = "Username should not be null")
    @NotBlank(message = "Username should not be blank")
    private String username;

    private String firstName;
    private String lastName;

    @NotNull(message = "Password should not be null")
    @Length(min = 8, max = 25, message = "Password should contain from 8 to 25 signs")
    private String password;

    @NotNull(message = "isAdmin should not be null")
    private Boolean isAdmin;
}
