package com.example.backend1.Controllers;

import com.example.backend1.Models.Item;
import com.example.backend1.Repositories.ItemRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRepo itemRepo;

    @BeforeEach
    public void init() {

        Item i1 = new Item(1L,"Shoe", 130.5f);
        Item i2 = new Item(2L,"Book", 200);
        Item i3 = new Item(3L,"Game Boy", 999);

        when(itemRepo.findAll()).thenReturn(Arrays.asList(i1,i2,i3));
        when(itemRepo.findById(1L)).thenReturn(Optional.of(i1));
        when(itemRepo.findById(2L)).thenReturn(Optional.of(i2));
        when(itemRepo.findById(3L)).thenReturn(Optional.of(i3));

    }

    @Test
    public void getAllItems() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 1,\"name\": \"Shoe\",\"price\": 130.5}," +
                        "{\"id\": 2,\"name\": \"Book\",\"price\": 200}," +
                        "{\"id\": 3,\"name\": \"Game Boy\",\"price\": 999}]"));
    }

    @Test
    public void getItemById() throws Exception {
        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 1,\"name\": \"Shoe\",\"price\": 130.5}"));
    }

    @Test
    void addItem() throws Exception {
        this.mockMvc.perform(post("/items/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1,\"name\":\"Shoe\",\"price\":1500}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The following item was added: Shoe, price: 1500")));
    }
}
