package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.UserRepository;
import com.gmail.vanyasudnishnikov.model.User;
import com.gmail.vanyasudnishnikov.model.enums.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    @Override
    public User getUserByUsername(Connection connection, String username) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, username, password, role FROM users  WHERE username=?"
        )) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getUserFromTable(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new SQLException("");
        }
        return null;
    }

    private User getUserFromTable(ResultSet resultSet) throws SQLException {
        Integer userId = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String userPassword = resultSet.getString("password");
        String role = resultSet.getString("role");
        RoleEnum userRole = RoleEnum.valueOf(role);
        return User.builder()
                .id(userId)
                .username(username)
                .password(userPassword)
                .role(userRole)
                .build();
    }
}