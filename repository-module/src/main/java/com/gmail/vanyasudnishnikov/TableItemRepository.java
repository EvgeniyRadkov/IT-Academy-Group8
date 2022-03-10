package com.gmail.vanyasudnishnikov;

import com.gmail.vanyasudnishnikov.model.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TableItemRepository {
    List<Item> getItems(Connection connection) throws SQLException;

    Integer deleteItems(Connection connection, String status) throws SQLException;

    List<Item> getItems(Connection connection, String status) throws SQLException;
}
