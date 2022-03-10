package com.gmail.vanyasudnishnikov.controllers;

import com.gmail.vanyasudnishnikov.controllers.DeleteItemController;
import com.gmail.vanyasudnishnikov.impl.ItemsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DeleteItemController.class)
class DeleteItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemsServiceImpl itemsService;

    @Test
    public void shouldReturn200WhenDeleteItemValid() throws Exception{
       String status = "COMPLETED";
       when(itemsService.delete(status)).thenReturn(1);
       mockMvc.perform(delete("/api/item/{status}", status)
               .contentType("application/json"))
               .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WenInvalidUrlForDelete() throws Exception {
        mockMvc.perform(delete("/api/item/{status}")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn405whenInvalidMethodForDelete() throws Exception {
        mockMvc.perform(get("/api/item/{status}")
                        .contentType("application/json"))
                .andExpect(status().isMethodNotAllowed());
    }
}
