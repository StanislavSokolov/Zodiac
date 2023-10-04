package ru.sokolov.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sokolov.models.User;
import ru.sokolov.services.UserService;
import ru.sokolov.util.UserValidatorAuthentication;
import ru.sokolov.util.UserValidatorAuthorization;
import ru.sokolov.util.UserValidatorTokenOzon;
import ru.sokolov.util.UserValidatorTokenWB;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/account")
public class PersonalAccountController {
    @Autowired
    private final UserService userService;
    private final UserValidatorAuthentication userValidatorAuthentication;
    private final UserValidatorAuthorization userValidatorAuthorization;
    private final UserValidatorTokenWB userValidatorTokenWB;
    private final UserValidatorTokenOzon userValidatorTokenOzon;

    public PersonalAccountController(UserService userService, UserValidatorAuthentication userValidatorAuthentication, UserValidatorAuthorization userValidatorAuthorization, UserValidatorTokenWB userValidatorTokenWB, UserValidatorTokenOzon userValidatorTokenOzon) {
        this.userService = userService;
        this.userValidatorAuthentication = userValidatorAuthentication;
        this.userValidatorAuthorization = userValidatorAuthorization;
        this.userValidatorTokenWB = userValidatorTokenWB;
        this.userValidatorTokenOzon = userValidatorTokenOzon;
    }

    @GetMapping("/settings")
    public String settingPage(@ModelAttribute("user") User user,
                              Model model,
                              @CookieValue(value = "Authorization", required = false) String authorization,
                              @CookieValue(value = "Client", required = false) String client) {

        if (authorization != null) {
            if (authorization.equals("true")) {
                ArrayList<String> shops = new ArrayList<>();
                User userDB = userService.findOne(Integer.valueOf(client));
                System.out.println(userDB.getTokenClientOzon());
                System.out.println(userDB.getTokenAdvertisingWB());
                shops.add("dd");
//                if ((userDB.getTokenClientOzon() == null) & (userDB.getTokenStatisticOzon() == null)) shops.add("Ozon");
//                if ((userDB.getTokenStandartWB() != null) & (userDB.getTokenStatisticWB() != null) & (userDB.getTokenAdvertisingWB() != null)) shops.add("Wildberries");
//                System.out.println(shops.get(0));
                model.addAttribute("shops", shops);
                model.addAttribute("user", userDB);
                return "account/settings";
            }
            else {
                return "auth/authorization";
            }
        } else {
            return "auth/authorization";
        }
    }

    @PostMapping("/settings/wb")
    public String addTokenWB(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @CookieValue(value = "Authorization", required = false) String authorization,
                             @CookieValue(value = "Client", required = false) String client) {
        userValidatorTokenWB.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "account/settings";

        userService.updateTokenWB(Integer.valueOf(client), user);
        return "redirect:/account/settings"; // и переходим в лк (в раздел настройки)
    }

    @PostMapping("/settings/ozon")
    public String addTokenOzon(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                               @CookieValue(value = "Authorization", required = false) String authorization,
                               @CookieValue(value = "Client", required = false) String client) {
        userValidatorTokenOzon.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "/account/settings";

        userService.updateTokenOzon(Integer.valueOf(client), user);
        return "redirect:/account/settings"; // и переходим в лк (в раздел настройки)
    }
}
