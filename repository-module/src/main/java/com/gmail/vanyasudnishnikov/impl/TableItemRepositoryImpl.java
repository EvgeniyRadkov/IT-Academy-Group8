package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.TableItemRepository;
import com.gmail.vanyasudnishnikov.model.Item;
import com.gmail.vanyasudnishnikov.model.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class TableItemRepositoryImpl implements TableItemRepository {
    @Override
    public List<Item> getItems(Connection connection) throws SQLException {
        List<Item> itemList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, name, description, status FROM item"
        )) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Item itemFromTable = getItemFromTable(resultSet);
                itemList.add(itemFromTable);
            }
            return itemList;
        }
    }

    @Override
    public Integer deleteItems(Connection connection, String status) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM item WHERE status=?")
        ) {
            preparedStatement.setString(1, status);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SQLException("failed to delete items.");
        }
    }

    @Override
    public List<Item> getItems(Connection connection, String status) throws SQLException {
        List<Item> itemList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, name, description, status FROM item WHERE status=?"
        )) {
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Item itemFromTable = getItemFromTable(resultSet);
                itemList.add(itemFromTable);
            }
            return itemList;
        }
    }

    private Item getItemFromTable(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String status = resultSet.getString("status");
        StatusEnum statusEnum = StatusEnum.valueOf(status);
        return Item.builder()
                .id(id)
                .name(name)
                .description(description)
                .status(statusEnum)
                .build();
    }
}