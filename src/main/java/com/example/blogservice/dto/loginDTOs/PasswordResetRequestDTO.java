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
public class PasswordResetRequestDTO {

    @NotNull(message = "Password should not be null")
    @Length(min = 8, max = 25, message = "Password should contain from 8 tp 25 signs")
    private String password;
}
