package com.gmail.vanyasudnishnikov.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vanyasudnishnikov.impl.ItemsServiceImpl;
import com.gmail.vanyasudnishnikov.model.ItemDTO;
import com.gmail.vanyasudnishnikov.model.enums.StatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GetItemsController.class)
class GetItemsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ItemsServiceImpl itemsService;

    @Test
    void shouldReturn200WhenValidGet() throws Exception {
        ItemDTO itemDTO = ItemDTO.builder()
                .name("admin")
                .description("admin")
                .status(StatusEnum.COMPLETED)
                .build();
        when(itemsService.getItems()).thenReturn(Collections.singletonList(itemDTO));
        mockMvc.perform(get("/api/items")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WenInvalidUrlForGet() throws Exception {
        mockMvc.perform(get("/api/items")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}