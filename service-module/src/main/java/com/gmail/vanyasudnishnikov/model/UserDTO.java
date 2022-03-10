package com.gmail.vanyasudnishnikov.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    public Integer id;
    private String username;
    private String password;
    private List<RoleDTOEnum> role;
}
