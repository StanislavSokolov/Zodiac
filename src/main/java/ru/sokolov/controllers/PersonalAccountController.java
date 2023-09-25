package ru.sokolov.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sokolov.models.User;
import ru.sokolov.services.UserService;
import ru.sokolov.util.UserValidatorAuthentication;
import ru.sokolov.util.UserValidatorAuthorization;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/account")
public class PersonalAccountController {
    @Autowired
    private final UserService userService;
    private final UserValidatorAuthentication userValidatorAuthentication;
    private final UserValidatorAuthorization userValidatorAuthorization;

    public PersonalAccountController(UserService userService, UserValidatorAuthentication userValidatorAuthentication, UserValidatorAuthorization userValidatorAuthorization) {
        this.userService = userService;
        this.userValidatorAuthentication = userValidatorAuthentication;
        this.userValidatorAuthorization = userValidatorAuthorization;
    }

    @GetMapping("/settings")
    public String settingPage(@ModelAttribute("user") User user, @CookieValue(value = "Authorization", required = false) String authorization, HttpServletResponse httpServletResponse) {

        if (authorization != null) {
            if (authorization.equals("true")) return "main/start";
            else {
//                model.addAttribute("message", "Hello,  MainController");
                Cookie cookie = new Cookie("Authorization", "true");
                cookie.setMaxAge(60);
                httpServletResponse.addCookie(cookie);
                return "first/hello";
            }
        } else {
            return "account/setting";
        }
    }

    @PostMapping()
    public String authenticationUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidatorAuthentication.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/authentication";

        userService.save(user); // если данные формы введены корректно, то сохраняем пользовтеля
        return "redirect:/"; // и переходим в лк
    }
}
