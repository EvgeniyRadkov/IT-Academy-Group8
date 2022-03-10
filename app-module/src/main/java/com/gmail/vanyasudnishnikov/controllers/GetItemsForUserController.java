package com.gmail.vanyasudnishnikov.controllers;

import com.gmail.vanyasudnishnikov.ItemsService;
import com.gmail.vanyasudnishnikov.model.ItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/items/{status}")
public class GetItemsForUserController {
    private ItemsService itemsService;

    @PostMapping
    @PostAuthorize("hasAuthority('USER')")
    public List<ItemDTO> getItems(@PathVariable String status) {
        List<ItemDTO> items = itemsService.getItems(status);
        return items;
    }
}
