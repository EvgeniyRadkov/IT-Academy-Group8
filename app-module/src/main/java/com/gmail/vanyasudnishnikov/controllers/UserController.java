package com.gmail.vanyasudnishnikov.controllers;

import com.gmail.vanyasudnishnikov.AddingUserService;
import com.gmail.vanyasudnishnikov.model.AddUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/user", consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private AddingUserService addingUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddUserDTO addUser(@RequestBody @Validated AddUserDTO addUserDTO) {
        return addingUserService.add(addUserDTO);
    }
}
