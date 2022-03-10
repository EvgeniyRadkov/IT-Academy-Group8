package com.gmail.vanyasudnishnikov.controllers;

import com.gmail.vanyasudnishnikov.model.enums.RoleEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
    private Integer id;
    private String username;
    private RoleEnum role;
    private String jwtToken;
}
