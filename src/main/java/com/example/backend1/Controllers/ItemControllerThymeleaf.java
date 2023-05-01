package com.example.backend1.Controllers;

import com.example.backend1.Models.Item;
import com.example.backend1.Repositories.ItemRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ItemControllerThymeleaf {

    private final ItemRepo itemRepo;


    public ItemControllerThymeleaf(ItemRepo itemRepo) {
        this.itemRepo=itemRepo;
    }

    @RequestMapping("items/all")
    public String getItems(Model model) {
        List<Item> items = itemRepo.findAll();
        model.addAttribute("allItems", items);
        model.addAttribute("itemName", "Name");
        model.addAttribute("itemPrice", "Price");
        model.addAttribute("itemTitle", "All Items");
        return "getItems";
    }
}
