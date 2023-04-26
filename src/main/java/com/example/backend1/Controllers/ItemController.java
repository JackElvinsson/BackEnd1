package com.example.backend1.Controllers;

import com.example.backend1.Models.Item;
import com.example.backend1.Repositories.ItemRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final ItemRepo itemRepo;

    public ItemController(ItemRepo itemRepo) {
        this.itemRepo=itemRepo;
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
