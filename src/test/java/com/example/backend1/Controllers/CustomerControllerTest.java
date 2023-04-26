package com.example.backend1.Controllers;

import com.example.backend1.Models.Customer;
import com.example.backend1.Repositories.CustomerRepo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepo customerRepo;

    @BeforeEach
    public void init() {
        Customer c1 = new Customer(1L, "Test Testsson", null);
        Customer c2 = new Customer(2L, "Test Testberg", null);
        Customer c3 = new Customer(3L, "Test Testman", null);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(c1));
        when(customerRepo.findById(2L)).thenReturn(Optional.of(c2));
        when(customerRepo.findById(3L)).thenReturn(Optional.of(c3));
        when(customerRepo.findAll()).thenReturn(Arrays.asList(c1, c2, c3));
    }

    @Test
    public void getAllCustomersTest() throws Exception {
        this.mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Test Testsson\", \"orderList\":null,\"id\":1}," +
                        "{\"name\":\"Test Testberg\", \"orderList\":null,\"id\":2}," +
                        "{\"name\":\"Test Testman\", \"orderList\":null,\"id\":3}]"));
    }

    @Test
    public void findCustomerByIdTest() throws Exception {
        this.mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Test Testsson\", \"orderList\":null,\"id\":1}"));
    }
}