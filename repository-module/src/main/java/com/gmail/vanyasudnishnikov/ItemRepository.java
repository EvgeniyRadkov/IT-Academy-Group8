package com.gmail.vanyasudnishnikov;

import com.gmail.vanyasudnishnikov.model.Item;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemRepository {
    Item updateStatusInTable(Connection connection, Long id, String status) throws SQLException;
}