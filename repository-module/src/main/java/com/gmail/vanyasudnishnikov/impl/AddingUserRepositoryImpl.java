package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.AddingUserRepository;
import com.gmail.vanyasudnishnikov.model.User;
import com.gmail.vanyasudnishnikov.model.enums.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Slf4j
public class AddingUserRepositoryImpl implements AddingUserRepository {
    @Override
    public User addInTableUsers(Connection connection, User user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users (username, password, role) VALUES (?,?,?)",
                Statement.RETURN_GENERATED_KEYS)
        ) {
            String username = user.getUsername();
            preparedStatement.setString(1, username);
            String userPassword = user.getPassword();
            preparedStatement.setString(2, userPassword);
            RoleEnum userRole = user.getRole();
            String role = String.valueOf(userRole);
            preparedStatement.setString(3, role);
            int update = preparedStatement.executeUpdate();
            if (update == 0) {
                throw new SQLException("Creating role failed");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int keysInt = generatedKeys.getInt(1);
                    user.setId(keysInt);
                } else {
                    throw new SQLException("Creating role failed, no ID obtained.");
                }
            }
            return user;
        }
    }
}
