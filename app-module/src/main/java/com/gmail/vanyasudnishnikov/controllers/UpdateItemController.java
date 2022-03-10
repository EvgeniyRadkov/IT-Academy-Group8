package com.gmail.vanyasudnishnikov.controllers;

import com.gmail.vanyasudnishnikov.UpdateItemService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/item/{id}/{status}")
public class UpdateItemController {
    private UpdateItemService updateItemService;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public Boolean addItem(@PathVariable Long id, @PathVariable String status) {
        return updateItemService.newStatus(id, status);
    }
}
