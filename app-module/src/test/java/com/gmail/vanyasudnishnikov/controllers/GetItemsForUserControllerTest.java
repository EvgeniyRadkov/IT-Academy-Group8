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
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GetItemsForUserController.class)
class GetItemsForUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ItemsServiceImpl itemsService;

    @Test
    void shouldReturn200WhenValidGet() throws Exception {
        String status = "COMPLETED";
        ItemDTO itemDTO = ItemDTO.builder()
                .name("user")
                .description("user")
                .status(StatusEnum.COMPLETED)
                .build();
        when(itemsService.getItems(status)).thenReturn(Collections.singletonList(itemDTO));
        mockMvc.perform(get("/api/items/{status}")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn405whenInValidMethodForGet() throws Exception {
        mockMvc.perform(post("/api/items/{status}", "COMPLETED")
                        .contentType("application/json"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldReturnItemForUserWhenValidGet() throws Exception {
        String status = "COMPLETED";
        ItemDTO itemDTO = ItemDTO.builder()
                .name("user")
                .description("user")
                .status(StatusEnum.COMPLETED)
                .build();
        when(itemsService.getItems(status)).thenReturn(Collections.singletonList(itemDTO));
        MvcResult mvcResult = mockMvc.perform(get("/api/items/{status}", status)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(itemDTO));
    }
}