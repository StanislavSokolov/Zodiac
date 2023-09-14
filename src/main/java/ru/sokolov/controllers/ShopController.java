package ru.sokolov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @GetMapping
    public String shopPage(@RequestParam(value = "shop", required = false) String shop,
                            @RequestParam(value = "period", required = false) String period,
                            Model model) {

        // System.out.println("Hello, " + name + " " + surname);
        model.addAttribute("message", "Hello, " + shop + " " + period);

        return "first/hello";
    }
}
