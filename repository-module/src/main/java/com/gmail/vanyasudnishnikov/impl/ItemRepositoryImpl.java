package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.ItemRepository;
import com.gmail.vanyasudnishnikov.model.Item;
import com.gmail.vanyasudnishnikov.model.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public Item updateStatusInTable(Connection connection, Long id, String status) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE item SET status=? WHERE id=?")
        ) {
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, id);
            int update = preparedStatement.executeUpdate();
            if (update == 0) {
                throw new SQLException("could not find item by id.");
            }
            return Item.builder()
                    .id(Math.toIntExact(id))
                    .status(StatusEnum.valueOf(status))
                    .build();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new SQLException("failed to update item in table.");
        }
    }
}
