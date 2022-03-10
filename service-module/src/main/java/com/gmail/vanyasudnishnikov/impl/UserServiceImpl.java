package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.UserRepository;
import com.gmail.vanyasudnishnikov.UserService;
import com.gmail.vanyasudnishnikov.model.User;
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
public class UserServiceImpl implements UserService {
    private DataSource dataSource;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Boolean isValid(String username, String password) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserByUsername(connection, username);
                connection.commit();
                if (user != null) {
                    return passwordEncoder.matches(password, user.getPassword());
                }
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public User findByUsername(String username) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserByUsername(connection, username);
                connection.commit();
                if (user != null) {
                    return user;
                }
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
