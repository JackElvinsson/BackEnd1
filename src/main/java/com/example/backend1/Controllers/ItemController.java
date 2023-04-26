package com.example.backend1.Controllers;

import com.example.backend1.Models.Item;
import com.example.backend1.Repositories.ItemRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
