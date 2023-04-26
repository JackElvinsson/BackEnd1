package com.example.backend1.Controllers;

import com.example.backend1.Models.Order;
import com.example.backend1.Repositories.CustomerRepo;
import com.example.backend1.Repositories.OrderRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final OrderRepo orderRepository;
    private final CustomerRepo customerRepository;

    public OrderController(OrderRepo orderRepository, CustomerRepo customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping("orders")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @RequestMapping("orders")
    public List<Order> getOrdersByCustomerId(@RequestParam Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            throw new RuntimeException("No matching orders found for customer id: " + customerId);
        }
        return orders;
    }

}
