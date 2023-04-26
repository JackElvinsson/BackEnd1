package com.example.backend1.Controllers;

import com.example.backend1.Models.Order;
import com.example.backend1.Repositories.CustomerRepo;
import com.example.backend1.Repositories.OrderRepo;
import org.springframework.web.bind.annotation.PathVariable;
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
        if (orderRepository.findAll().isEmpty()) {
            throw new RuntimeException("No orders found");
        }
        return orderRepository.findAll();
    }

    @RequestMapping("orders/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            throw new RuntimeException("No matching orders found for customer id: " + customerId);
        }
        return orders;
    }

}
