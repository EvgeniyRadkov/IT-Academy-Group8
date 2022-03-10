package com.gmail.vanyasudnishnikov;

import com.gmail.vanyasudnishnikov.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface AddingUserRepository {
    User addInTableUsers(Connection connection, User user) throws SQLException;
}