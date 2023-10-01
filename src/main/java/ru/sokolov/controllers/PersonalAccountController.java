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
import javax.servlet.http.HttpServletRequest;
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
    public String settingPage(@ModelAttribute("user") User user,
                              @CookieValue(value = "Authorization", required = false) String authorization,
                              @CookieValue(value = "Client", required = false) String client) {

        if (authorization != null) {
            if (authorization.equals("true")) return "account/settings";
            else {
                return "auth/authorization";
            }
        } else {
            return "account/settings";
        }
    }

    @PostMapping("/settings/wb")
    public String addTokenWB(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                     HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse) {
        userValidatorAuthentication.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/authentication";

        return "redirect:/account/settings"; // и переходим в лк (в раздел настройки)
    }

    @PostMapping("/settings/ozon")
    public String addTokenOzon(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                     HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse) {
        userValidatorAuthentication.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/authentication";

        return "redirect:/account/settings"; // и переходим в лк (в раздел настройки)
    }
}
