package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.ItemsService;
import com.gmail.vanyasudnishnikov.TableItemRepository;
import com.gmail.vanyasudnishnikov.model.Item;
import com.gmail.vanyasudnishnikov.model.ItemDTO;
import com.gmail.vanyasudnishnikov.model.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ItemsServiceImpl implements ItemsService {
    private TableItemRepository tableItemRepository;
    private DataSource dataSource;

    @Override
    public List<ItemDTO> getItems() {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> itemList = tableItemRepository.getItems(connection);
                connection.commit();
                return convertToListItemDTO(itemList);
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Integer delete(String status) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Integer countItemsDelete = tableItemRepository.deleteItems(connection, status);
                connection.commit();
                return countItemsDelete;
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<ItemDTO> getItems(String status) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> itemList = tableItemRepository.getItems(connection, status);
                connection.commit();
                return convertToListItemDTO(itemList);
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private List<ItemDTO> convertToListItemDTO(List<Item> itemList) {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item : itemList) {
            String itemName = item.getName();
            String itemDescription = item.getDescription();
            StatusEnum itemStatus = item.getStatus();
            ItemDTO itemDTO = ItemDTO.builder()
                    .name(itemName)
                    .description(itemDescription)
                    .status(itemStatus)
                    .build();
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }
}
