package ru.sokolov.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sokolov.models.Person;
import ru.sokolov.models.User;
import ru.sokolov.services.UserService;
import ru.sokolov.util.UserValidator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final UserService userService;
    private final UserValidator userValidator;

    public AuthController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String authPage(@ModelAttribute("user") User user, @CookieValue(value = "Authentication", required = false) String authentication, HttpServletResponse httpServletResponse) {

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
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/Регистрация";

        userService.save(user); // если данные формы введены корректно, то сохраняем пользовтеля
        return "redirect:/"; // и переходим в лк
    }
}
