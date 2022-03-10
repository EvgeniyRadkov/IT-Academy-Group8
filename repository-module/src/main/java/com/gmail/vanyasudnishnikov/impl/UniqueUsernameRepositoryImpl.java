package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.UniqueUsernameRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UniqueUsernameRepositoryImpl implements UniqueUsernameRepository {
    @Override
    public String checkInTableUsers(Connection connection, String username) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT username FROM users WHERE username=? "
        )) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("username");
            }
            return null;
        }
    }
}
