package ru.sokolov.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sokolov.models.Person;
import ru.sokolov.services.PeopleService;
import ru.sokolov.util.PersonValidator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    public AuthController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String authPage(@ModelAttribute("person") Person person, @CookieValue(value = "Authentication", required = false) String authentication, HttpServletResponse httpServletResponse) {

        if (authentication != null) {
            if (authentication.equals("true")) return "main/start";
            else {
//                model.addAttribute("message", "Hello,  MainController");
                Cookie cookie = new Cookie("Authentication", "true");
                cookie.setMaxAge(60);
                httpServletResponse.addCookie(cookie);
                return "first/hello";
            }
        } else {
            return "auth/Регистрация";
        }

    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/Регистрация";

//        personDAO.save(person);
        peopleService.save(person);
        return "redirect:/";
    }
}
