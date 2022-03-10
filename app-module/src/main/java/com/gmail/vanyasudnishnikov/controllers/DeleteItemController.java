package com.gmail.vanyasudnishnikov.controllers;

import com.gmail.vanyasudnishnikov.ItemsService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/item/{status}")
public class DeleteItemController {
    private ItemsService itemsService;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public Integer addItem(@PathVariable String status) {
        return itemsService.delete(status);
    }
}
