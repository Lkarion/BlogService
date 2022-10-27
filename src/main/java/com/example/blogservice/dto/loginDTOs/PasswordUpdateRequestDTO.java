package com.example.blogservice.dto.loginDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PasswordUpdateRequestDTO {
    @NotNull
    private String username;

    @NotNull
    @Length(min = 8, max = 25)
    private String oldPassword;

    @NotNull
    @Length(min = 8, max = 25)
    private String newPassword;
}
