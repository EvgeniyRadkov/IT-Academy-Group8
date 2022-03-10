package com.gmail.vanyasudnishnikov.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vanyasudnishnikov.impl.UpdateItemServiceImpl;
import com.gmail.vanyasudnishnikov.model.Item;
import com.gmail.vanyasudnishnikov.model.enums.StatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UpdateItemController.class)
class UpdateItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UpdateItemServiceImpl updateItemService;

    @Test
    void shouldReturn200WhenValidUpdate() throws Exception {
        Integer id = 1;
        String status = "COMPLETED";
        Item item = Item.builder()
                .id(id)
                .status(StatusEnum.valueOf(status))
                .build();
        when(updateItemService.newStatus(Long.valueOf(id), status)).thenReturn(true);
        mockMvc.perform(put("/api/item/{id}/{status}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WenInvalidUrlForPut() throws Exception {
        mockMvc.perform(put("/api/item/{id}/{status}")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenUpdatedItemStatusIsBlank() throws Exception {
        Integer id = 1;
        String status = "";
        Item item = Item.builder()
                .id(id)
                .status(StatusEnum.valueOf(status))
                .build();
        when(updateItemService.newStatus(Long.valueOf(id), status)).thenReturn(false);
        mockMvc.perform(put("/api/item/{id}/{status}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200WhenUpdatedItemStatusIsEnumStatus() throws Exception {
        Integer id = 1;
        String status = "COMPLETED";
        Item item = Item.builder()
                .id(id)
                .status(StatusEnum.valueOf(status))
                .build();
        when(updateItemService.newStatus(Long.valueOf(id), status)).thenReturn(true);
        mockMvc.perform(put("/api/item/{id}/{status}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn400WhenUpdatedItemStatusIsNotEnumStatus() throws Exception {
        Integer id = 1;
        String status = "completed";
        Item item = Item.builder()
                .id(id)
                .status(StatusEnum.valueOf(status))
                .build();
        when(updateItemService.newStatus(Long.valueOf(id), status)).thenReturn(true);
        mockMvc.perform(put("/api/item/{id}/{status}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WithMessageWhenUpdatedItemStatusIsNotEnumStatus() throws Exception {
        Integer id = 1;
        String status = "completed";
        Item item = Item.builder()
                .id(id)
                .status(StatusEnum.valueOf(status))
                .build();
        MvcResult mvcResult = mockMvc.perform(put("/api/item/{id}/{status}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Please,enter status in upper case", errors.get(0));
    }

    @Test
    void shouldReturn400WithMessageWhenUpdatedItemStatusIsBlank() throws Exception {
        Integer id = 1;
        String status = "completed";
        Item item = Item.builder()
                .id(id)
                .status(StatusEnum.valueOf(status))
                .build();
        MvcResult mvcResult = mockMvc.perform(put("/api/item/{id}/{status}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Please enter a status (not blank)", errors.get(0));
    }
}