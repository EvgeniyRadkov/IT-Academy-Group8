package com.gmail.vanyasudnishnikov.controllers;

import com.gmail.vanyasudnishnikov.UserService;
import com.gmail.vanyasudnishnikov.model.AddUserDTO;
import com.gmail.vanyasudnishnikov.model.User;
import com.gmail.vanyasudnishnikov.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/token", consumes = MediaType.APPLICATION_JSON_VALUE)
public class CreateTokenController {
    private UserService userService;
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<Object> authenticateUser(@RequestBody AddUserDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        Boolean isValid = userService.isValid(username, password);
        if (isValid) {
            String jwtToken = jwtUtils.generateJwtToken(username);
            User user = userService.findByUsername(username);
            return ResponseEntity.ok(
                    new JwtResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getRole(),
                            jwtToken
                    )
            );
        } else {
            return ResponseEntity.badRequest()
                    .body("login or password os not valid");
        }

    }
}
