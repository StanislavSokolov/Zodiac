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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/account")
public class PersonalAccountController {
    @Autowired
    private final UserService userService;
    private final UserValidatorTokenWB userValidatorTokenWB;
    private final UserValidatorTokenOzon userValidatorTokenOzon;

    public PersonalAccountController(UserService userService, UserValidatorTokenWB userValidatorTokenWB, UserValidatorTokenOzon userValidatorTokenOzon) {
        this.userService = userService;
        this.userValidatorTokenWB = userValidatorTokenWB;
        this.userValidatorTokenOzon = userValidatorTokenOzon;
    }

    @GetMapping("/stock")
    public String stockPage(@ModelAttribute("user") User user,
                             Model model,
                             @CookieValue(value = "Authorization", required = false) String authorization,
                             @CookieValue(value = "Client", required = false) String client,
                             HttpServletResponse httpServletResponse) {

        if (authorization != null) {
            if (authorization.equals("true")) {
                ArrayList<String> shops = new ArrayList<>();
                User userDB = userService.findOne(Integer.valueOf(client));
                if ((userDB.getTokenClientOzon() != null) & (userDB.getTokenStatisticOzon() != null)) shops.add("Ozon");
                if ((userDB.getTokenStandartWB() != null) & (userDB.getTokenStatisticWB() != null) & (userDB.getTokenAdvertisingWB() != null)) shops.add("Wildberries");
                model.addAttribute("shops", shops);
                model.addAttribute("user", userDB);
                String activeShop = "Wildberries";
                model.addAttribute("activeShop", activeShop);

                return "account/shop";
            }
            else {
                return "auth/authorization";
            }
        } else {
            return "auth/authorization";
        }
    }

    @GetMapping("/Wildberries")
    public String wbStatPage(@ModelAttribute("user") User user,
                             Model model,
                             @CookieValue(value = "Authorization", required = false) String authorization,
                             @CookieValue(value = "Client", required = false) String client,
                             HttpServletResponse httpServletResponse) {

        if (authorization != null) {
            if (authorization.equals("true")) {
                ArrayList<String> shops = new ArrayList<>();
                User userDB = userService.findOne(Integer.valueOf(client));
                if ((userDB.getTokenClientOzon() != null) & (userDB.getTokenStatisticOzon() != null)) shops.add("Ozon");
                if ((userDB.getTokenStandartWB() != null) & (userDB.getTokenStatisticWB() != null) & (userDB.getTokenAdvertisingWB() != null)) shops.add("Wildberries");
                model.addAttribute("shops", shops);
                model.addAttribute("user", userDB);
                String activeShop = "Wildberries";
                model.addAttribute("activeShop", activeShop);


                return "account/shop";
            }
            else {
                return "auth/authorization";
            }
        } else {
            return "auth/authorization";
        }
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
                if ((userDB.getTokenClientOzon() != null) & (userDB.getTokenStatisticOzon() != null)) shops.add("Ozon");
                if ((userDB.getTokenStandartWB() != null) & (userDB.getTokenStatisticWB() != null) & (userDB.getTokenAdvertisingWB() != null)) shops.add("Wildberries");
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

    @GetMapping("/out")
    public String outPage(@ModelAttribute("user") User user,
                          @CookieValue(value = "Authorization", required = false) String authorization,
                          @CookieValue(value = "Client", required = false) String client,
                          HttpServletResponse httpServletResponse) {

        if (authorization != null) {
            if (authorization.equals("true")) {
                Cookie cookieAuthorization = new Cookie("Authorization", "false");
                cookieAuthorization.setMaxAge(0);
                cookieAuthorization.setSecure(true);
                cookieAuthorization.setHttpOnly(true);
                cookieAuthorization.setPath("/");
                httpServletResponse.addCookie(cookieAuthorization);

                Cookie cookieClient = new Cookie("Client", "Qwxs12MM");
                cookieClient.setMaxAge(0);
                cookieClient.setSecure(true);
                cookieClient.setHttpOnly(true);
                cookieClient.setPath("/");
                httpServletResponse.addCookie(cookieClient);
                return "auth/authorization";
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
