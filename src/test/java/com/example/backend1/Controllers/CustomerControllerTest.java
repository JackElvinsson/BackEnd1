package com.example.backend1.Controllers;

import com.example.backend1.Models.Customer;
import com.example.backend1.Repositories.CustomerRepo;
import com.example.backend1.Repositories.OrderRepo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepo customerRepo;

    @MockBean
    private OrderRepo orderRepo;

    @BeforeEach
    public void init() {
        Customer c1 = new Customer(1L, "Test Testsson", "900110", null);
        Customer c2 = new Customer(2L, "Test Testberg", "800615", null);
        Customer c3 = new Customer(3L, "Test Testman", "701013", null);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(c1));
        when(customerRepo.findById(2L)).thenReturn(Optional.of(c2));
        when(customerRepo.findById(3L)).thenReturn(Optional.of(c3));
        when(customerRepo.findAll()).thenReturn(Arrays.asList(c1, c2, c3));
    }

    @Test
    public void getAllCustomersTest() throws Exception {
        this.mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Test Testsson\",\"ssn\":\"900110\", \"orderList\":null,\"id\":1}," +
                        "{\"name\":\"Test Testberg\",\"ssn\":\"800615\", \"orderList\":null,\"id\":2}," +
                        "{\"name\":\"Test Testman\",\"ssn\":\"701013\", \"orderList\":null,\"id\":3}]"));
    }

    @Test
    public void findCustomerByIdTest() throws Exception {
        this.mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Test Testsson\",\"ssn\":\"900110\", \"orderList\":null,\"id\":1}"));
    }

    @Test
    void addCustomerByPost() throws Exception {
        this.mockMvc.perform(post("/customers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Tester\",\"ssn\":\"500515\", \"orderList\":null,\"id\":5}"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("Added customer: Test Tester, 500515")));
    }
}