package com.gmail.vanyasudnishnikov.impl;

import com.gmail.vanyasudnishnikov.AddingItemRepository;
import com.gmail.vanyasudnishnikov.AddingItemService;
import com.gmail.vanyasudnishnikov.model.Item;
import com.gmail.vanyasudnishnikov.model.ItemDTO;
import com.gmail.vanyasudnishnikov.model.ViewItemDTO;
import com.gmail.vanyasudnishnikov.model.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
@AllArgsConstructor
@Slf4j
public class AddingItemServiceImpl implements AddingItemService {
    private DataSource dataSource;
    private AddingItemRepository addingItemRepository;

    @Override
    public ViewItemDTO add(ItemDTO itemDTO) {
        Item item = convertToItem(itemDTO);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item addedItem = addingItemRepository.addInTableItem(connection, item);
                connection.commit();
                return convertToViewItemDTO(addedItem);
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {

            log.error(e.getMessage(), e);
        }
        return null;
    }

    private Item convertToItem(ItemDTO itemDTO) {
        String itemDTOName = itemDTO.getName();
        String itemDTODescription = itemDTO.getDescription();
        StatusEnum itemDTOStatus = itemDTO.getStatus();
        return Item.builder()
                .name(itemDTOName)
                .description(itemDTODescription)
                .status(itemDTOStatus)
                .build();
    }

    private ViewItemDTO convertToViewItemDTO(Item addedItem) {
        Integer addedItemId = addedItem.getId();
        String addedItemName = addedItem.getName();
        ViewItemDTO viewItemDTO = new ViewItemDTO();
        viewItemDTO.setId(addedItemId);
        viewItemDTO.setName(addedItemName);
        return viewItemDTO;
    }
}
