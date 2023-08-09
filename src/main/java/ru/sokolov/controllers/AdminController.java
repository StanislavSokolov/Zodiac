package ru.sokolov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import ru.sokolov.dao.PersonDAO;
import ru.sokolov.models.Person;

@Controller
@RequestMapping("/admin")
public class AdminController {
//    private  final PersonDAO personDAO;
//
//    public AdminController(PersonDAO personDAO) {
//        this.personDAO = personDAO;
//    }
//
//    @GetMapping()
//    public String adminPage(Model model, @ModelAttribute("person") Person person) {
//        model.addAttribute("people", personDAO.index());
//
//        return "people/adminPage";
//    }
//
//    @PatchMapping("/add")
//    public String makeAdmin(@ModelAttribute("person") Person person) {
//        System.out.println(person.getId());
//
//        return "redirect:/people";
//    }
}
