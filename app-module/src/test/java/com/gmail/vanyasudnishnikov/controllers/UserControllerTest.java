package com.gmail.vanyasudnishnikov.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vanyasudnishnikov.impl.AddingUserServiceImpl;
import com.gmail.vanyasudnishnikov.model.AddUserDTO;
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
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AddingUserServiceImpl addingUserService;

    @Test
    void shouldReturn200WhenValidInput() throws Exception {
        AddUserDTO addUserDTO = AddUserDTO.builder()
                .username("admin")
                .password("$2a$12$uFRO0NcFDo.MjPBueoBQXONUqN2ILkKyRRkQHFykioKcCk.O91r32")
                .build();
        when(addingUserService.add(addUserDTO)).thenReturn(addUserDTO);
        mockMvc.perform(post("/api/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addUserDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WenInvalidUrlForPost() throws Exception {
        mockMvc.perform(post("/api/user")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn405whenInvalidMethodForPost() throws Exception {
        mockMvc.perform(get("/api/user")
                        .contentType("application/json"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldReturn400WhenAddedUsernameIsBlank() throws Exception {
        AddUserDTO addUserDTO = AddUserDTO.builder()
                .username("")
                .password("$2a$12$uFRO0NcFDo.MjPBueoBQXONUqN2ILkKyRRkQHFykioKcCk.O91r32")
                .build();
        when(addingUserService.add(addUserDTO)).thenReturn(addUserDTO);
        mockMvc.perform(post("/api/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenAddedUserPasswordIsBlank() throws Exception {
        AddUserDTO addUserDTO = AddUserDTO.builder()
                .username("admin")
                .password("")
                .build();
        when(addingUserService.add(addUserDTO)).thenReturn(addUserDTO);
        mockMvc.perform(post("/api/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldMapToBusinessModelWhenAddedUsernameIsBlank() throws Exception {
        AddUserDTO addUserDTO = AddUserDTO.builder()
                .username("")
                .password("$2a$12$uFRO0NcFDo.MjPBueoBQXONUqN2ILkKyRRkQHFykioKcCk.O91r32")
                .build();
        when(addingUserService.add(addUserDTO)).thenReturn(addUserDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addUserDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
        verify(addingUserService, times(0)).add(addUserDTO);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        AddUserDTO result = objectMapper.readValue(actualResponseBody, AddUserDTO.class);
        Assertions.assertNull(result.getUsername());
    }

    @Test
    void shouldMapToBusinessModelWhenAddedPasswordIsBlank() throws Exception {
        AddUserDTO addUserDTO = AddUserDTO.builder()
                .username("admin")
                .password("")
                .build();
        when(addingUserService.add(addUserDTO)).thenReturn(addUserDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addUserDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
        verify(addingUserService, times(0)).add(addUserDTO);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        AddUserDTO result = objectMapper.readValue(actualResponseBody, AddUserDTO.class);
        Assertions.assertNull(result.getPassword());
    }

    @Test
    void shouldReturn400WithMessageWhenAddedUsernameIsBlank() throws Exception {
        AddUserDTO addUserDTO = AddUserDTO.builder()
                .username("")
                .password("$2a$12$uFRO0NcFDo.MjPBueoBQXONUqN2ILkKyRRkQHFykioKcCk.O91r32")
                .build();
        when(addingUserService.add(addUserDTO)).thenReturn(addUserDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addUserDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Please enter username (not blank)", errors.get(0));
    }

    @Test
    void shouldReturn400WithMessageWhenAddedPasswordIsBlank() throws Exception {
        AddUserDTO addUserDTO = AddUserDTO.builder()
                .username("admin")
                .password("")
                .build();
        when(addingUserService.add(addUserDTO)).thenReturn(addUserDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addUserDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Please enter password (not blank)", errors.get(0));
    }
}