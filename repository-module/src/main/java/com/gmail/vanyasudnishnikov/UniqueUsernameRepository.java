package com.gmail.vanyasudnishnikov;

import java.sql.Connection;
import java.sql.SQLException;

public interface UniqueUsernameRepository {
    String checkInTableUsers(Connection connection, String username) throws SQLException;
}