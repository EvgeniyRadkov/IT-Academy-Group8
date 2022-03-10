package com.gmail.vanyasudnishnikov;

import com.gmail.vanyasudnishnikov.model.ItemDTO;

import java.util.List;

public interface ItemsService {
    List<ItemDTO> getItems();

    Integer delete(String status);

    List<ItemDTO> getItems(String status);
}
