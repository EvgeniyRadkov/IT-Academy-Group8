package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.AddingItemRepository;
import com.gmail.vanyasudnishnikov.model.Item;
import com.gmail.vanyasudnishnikov.model.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Slf4j
public class AddingItemRepositoryImpl implements AddingItemRepository {
    @Override
    public Item addInTableItem(Connection connection, Item item) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO item (name,description, status) VALUES (?,?,?)",
                Statement.RETURN_GENERATED_KEYS)
        ) {
            String itemName = item.getName();
            preparedStatement.setString(1, itemName);
            String description = item.getDescription();
            preparedStatement.setString(2, description);
            StatusEnum itemStatus = item.getStatus();
            String status = String.valueOf(itemStatus);
            preparedStatement.setString(3, status);
            int update = preparedStatement.executeUpdate();
            if (update == 0) {
                return null;
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating role failed, no ID obtained.");
                }
            }
            return item;
        }
    }
}
