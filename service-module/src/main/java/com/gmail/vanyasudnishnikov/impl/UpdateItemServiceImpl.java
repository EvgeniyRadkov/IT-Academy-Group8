package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.ItemRepository;
import com.gmail.vanyasudnishnikov.UpdateItemService;
import com.gmail.vanyasudnishnikov.model.Item;
import com.gmail.vanyasudnishnikov.model.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
@Slf4j
@AllArgsConstructor
public class UpdateItemServiceImpl implements UpdateItemService {
    private ItemRepository itemRepository;
    private DataSource dataSource;

    @Override
    public Boolean newStatus(Long id, String status) {
        StatusEnum statusEnum = StatusEnum.valueOf(status);
        if (statusEnum == StatusEnum.COMPLETED || statusEnum == StatusEnum.READY) {

        }
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = itemRepository.updateStatusInTable(connection, id, status);
                connection.commit();
                if (item != null) {
                    return true;
                }
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
