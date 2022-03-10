package com.gmail.vanyasudnishnikov.model;

import com.gmail.vanyasudnishnikov.model.enums.RoleEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter
public class User {
    private Integer id;
    private String username;
    private String password;
    private RoleEnum role;
}
