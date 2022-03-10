package com.gmail.vanyasudnishnikov.model;

import com.gmail.vanyasudnishnikov.validator.UniqueUsername;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Builder
@Setter
public class AddUserDTO {
    @UniqueUsername
    @NotNull(message = "Username cannot be empty")
    private String username;
    @NotNull(message = "Password cannot be empty")
    private String password;
}
