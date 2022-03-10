package com.gmail.vanyasudnishnikov.controllers;

import com.gmail.vanyasudnishnikov.AddingItemService;
import com.gmail.vanyasudnishnikov.model.ItemDTO;
import com.gmail.vanyasudnishnikov.model.ViewItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/item", consumes = MediaType.APPLICATION_JSON_VALUE)
public class AddItemController {
    private AddingItemService addingItemService;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public ViewItemDTO addItem(@RequestBody @Validated ItemDTO itemDTO) {
        ViewItemDTO addedItem = addingItemService.add(itemDTO);
        return addedItem;
    }
}
