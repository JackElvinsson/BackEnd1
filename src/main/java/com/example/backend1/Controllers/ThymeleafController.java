package com.example.backend1.Controllers;

import com.example.backend1.Models.Customer;
import com.example.backend1.Models.Item;
import com.example.backend1.Repositories.CustomerRepo;
import com.example.backend1.Repositories.ItemRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ThymeleafController {

    private final CustomerRepo customerRepo;
    private final ItemRepo itemRepo;
    ThymeleafController(CustomerRepo customerRepo, ItemRepo itemRepo) {
        this.customerRepo = customerRepo;
        this.itemRepo=itemRepo;
    }


    @RequestMapping("/customers/all")
    public String getAll(Model model) {
        List<Customer> customers = customerRepo.findAll();
        model.addAttribute("allCustomers", customers);
        model.addAttribute("customerName", "Name");
        model.addAttribute("customerSsn", "Ssn");
        return "getCustomers";
    }

    @RequestMapping("/customers/addbyform")
    public String showAddCustomerForm(Model model) {
        return "addCustomer";
    }

    @PostMapping("/customers/addByFormReceival")
    public String addCustomerByForm(@RequestParam String customerName,
                                    @RequestParam String SSN, Model model) {

        if (customerName.isEmpty() || SSN.isEmpty()) {

            model.addAttribute("errorMessage", "Invalid input. Namn eller SSN saknas.");

            return "addCustomer";
        }
        Customer newCustomer = new Customer();
        newCustomer.setName(customerName);
        newCustomer.setSsn(SSN);
        customerRepo.save(newCustomer);

        return "redirect:/customers/all";
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




    @RequestMapping("/items/addbyform")
    public String showAddItemForm(Model model) {
        return "addItem";
    }

    @PostMapping("/items/addByFormReceival")
    public String addItemByForm(@RequestParam String itemName,
                                @RequestParam(required = false) Float price, Model model) {

        if (itemName.isEmpty() || price == null) {

            model.addAttribute("errorMessage", "Invalid input. Item name or cost is missing.");

            return "addItem";
        }

        Item newItem = new Item();

        newItem.setName(itemName);
        newItem.setPrice(price);
        itemRepo.save(newItem);

        return "redirect:/items/all";
    }



}