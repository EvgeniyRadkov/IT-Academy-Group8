package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.AddingUserRepository;
import com.gmail.vanyasudnishnikov.AddingUserService;
import com.gmail.vanyasudnishnikov.model.AddUserDTO;
import com.gmail.vanyasudnishnikov.model.User;
import com.gmail.vanyasudnishnikov.model.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
@AllArgsConstructor
@Slf4j
public class AddingUserServiceImpl implements AddingUserService {
    private DataSource dataSource;
    private AddingUserRepository addingUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AddUserDTO add(AddUserDTO addUserDTO) {
        User user = convertToUser(addUserDTO);
        RoleEnum roleUser = RoleEnum.ROLE_USER;
        user.setRole(roleUser);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User addedUser = addingUserRepository.addInTableUsers(connection, user);
                connection.commit();
                return convertToUserDTO(addedUser);
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private AddUserDTO convertToUserDTO(User addedUser) {
        String username = addedUser.getUsername();
        String password = addedUser.getPassword();
        return AddUserDTO.builder()
                .username(username)
                .password(password)
                .build();
    }

    private User convertToUser(AddUserDTO addUserDTO) {
        String username = addUserDTO.getUsername();
        String password = addUserDTO.getPassword();
        String passwordEncoded = passwordEncoder.encode(password);
        return User.builder()
                .username(username)
                .password(passwordEncoded)
                .build();
    }
}
