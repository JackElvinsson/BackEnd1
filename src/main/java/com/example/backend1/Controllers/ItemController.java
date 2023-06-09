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
        if (item.getPrice() > 0 && item.getName() != null && !item.getName().isBlank()) {
            itemRepo.save(item);
            return "The following item was added: " + item.getName() + ", price: " + item.getPrice();
        } else {
            return "Could not add item";
        }
    }

    @PostMapping("items/buy")
    public String createPurchase(@RequestParam Long customerId, @RequestParam Long itemId) {

        // Fetch the customer and item based on their ids
        try {
            if (customerRepo.findById(customerId).isPresent() && itemRepo.findById(itemId).isPresent()) {

            Customer customer = customerRepo.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer with id " + customerId + " not found"));
            Item item = itemRepo.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Item with id " + itemId + " not found"));
            Order order = new Order(customer, Collections.singletonList(item));

            orderRepo.save(order);
            return "The following order was created: " + item.getName() + ", price: " + item.getPrice() + " | Customer: " + customer.getName() + ", ssn: " + customer.getSsn() + "";

            } else {
                return "Customer or item not found";
            }

        } catch (Exception e) {
            System.out.println("PARSE ERROR");
            return null;
        }
    }


    /* Same as above but with ResponseEntity */
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
