package com.example.backend1.Controllers;

import com.example.backend1.Models.Customer;
import com.example.backend1.Models.Item;
import com.example.backend1.Models.Order;
import com.example.backend1.Repositories.CustomerRepo;
import com.example.backend1.Repositories.ItemRepo;
import com.example.backend1.Repositories.OrderRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class ItemController {

    private final ItemRepo itemRepo;
    private final CustomerRepo customerRepo;
    private final OrderRepo orderRepo;

    public ItemController(ItemRepo itemRepo, CustomerRepo customerRepo, OrderRepo orderRepo) {
        this.itemRepo=itemRepo;
        this.customerRepo=customerRepo;
        this.orderRepo=orderRepo;
    }

    @RequestMapping("items")
    public List<Item> getItems() {
        return itemRepo.findAll();
    }

    @RequestMapping("items/{id}")
    public Item getItemById(@PathVariable long id) {
        return itemRepo.findById(id).orElseThrow(() -> new RuntimeException("No matching item found for id:" + id));
    }

    @PostMapping("items/add")
    public String addItem(@RequestBody Item item) {
        itemRepo.save(item);
        return "The following item was added: " + item.getName() + ", price: " + item.getPrice();
    }


    @PostMapping("items/buy")
    public Order createPurchase(@RequestParam Long customerId, @RequestParam Long itemId) {
        System.out.println("customerId = " + customerId);
        System.out.println("itemId = " + itemId);
        // Fetch the customer and item based on their ids
        try {
            Customer customer = customerRepo.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer with id " + customerId + " not found"));
            Item item = itemRepo.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Item with id " + itemId + " not found"));

            Order order = new Order(customer, Collections.singletonList(item));

            return orderRepo.save(order);
        } catch (Exception e) {
            System.out.println("PARSE ERROR");
            return null;
        }
    }


    /* Samma som ovan fast med ResponseEntity */
//    @PostMapping("items/buy")
//    public ResponseEntity<Order> createPurchase(@RequestParam Long customerId, @RequestParam Long itemId) {
//        System.out.println("customerId = " + customerId);
//        System.out.println("itemId = " + itemId);
//        // Fetch the customer and item based on their ids
//        try {
//            Customer customer = customerRepo.findById(customerId)
//                    .orElseThrow(() -> new RuntimeException("Customer with id " + customerId + " not found"));
//            Item item = itemRepo.findById(itemId)
//                    .orElseThrow(() -> new RuntimeException("Item with id " + itemId + " not found"));
//
//            Order order = new Order(customer, Collections.singletonList(item));
//            Order savedOrder = orderRepo.save(order);
//
//            return ResponseEntity.ok(savedOrder);
//        } catch (Exception e) {
//            System.out.println("PARSE ERROR");
//            return ResponseEntity.badRequest().build();
//        }
//    }


}
