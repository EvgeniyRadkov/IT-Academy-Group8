package com.gmail.vanyasudnishnikov;

import com.gmail.vanyasudnishnikov.model.Item;

import java.sql.Connection;
import java.sql.SQLException;

public interface AddingItemRepository {
    Item addInTableItem(Connection connection, Item item) throws SQLException;
}
