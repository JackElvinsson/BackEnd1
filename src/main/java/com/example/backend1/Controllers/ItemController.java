package com.example.backend1.Controllers;

import com.example.backend1.Models.Item;
import com.example.backend1.Repositories.CustomerRepo;
import com.example.backend1.Repositories.ItemRepo;
import com.example.backend1.Repositories.OrderRepo;
import org.springframework.web.bind.annotation.*;

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
}
