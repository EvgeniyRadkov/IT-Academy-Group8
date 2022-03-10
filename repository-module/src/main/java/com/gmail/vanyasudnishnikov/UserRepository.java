package com.gmail.vanyasudnishnikov;

import com.gmail.vanyasudnishnikov.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserRepository {
    User getUserByUsername(Connection connection, String username) throws SQLException;
}
