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
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final UserService userService;
    private final UserValidatorAuthentication userValidatorAuthentication;
    private final UserValidatorAuthorization userValidatorAuthorization;

    public AuthController(UserService userService, UserValidatorAuthentication userValidatorAuthentication, UserValidatorAuthorization userValidatorAuthorization) {
        this.userService = userService;
        this.userValidatorAuthentication = userValidatorAuthentication;
        this.userValidatorAuthorization = userValidatorAuthorization;
    }

    @GetMapping
    public String authenticationPage(@ModelAttribute("user") User user,
                                     @CookieValue(value = "Authorization", required = false) String authorization,
                                     @CookieValue(value = "Client", required = false) String client) {

        if (authorization != null) {
            if (authorization.equals("true")) return "main/exit";
                // показать страницу статистики магазина, используя куки
            else {
                return "auth/authentication";
            }
        } else {
            return "auth/authentication";
        }
    }

    @PostMapping()
    public String authenticationUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                     HttpServletResponse httpServletResponse) {
        userValidatorAuthentication.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/authentication";

        userService.save(user); // если данные формы введены корректно, то сохраняем пользователя
        // получаем уникальный номер

        Cookie cookieAuthorization = new Cookie("Authorization", "true");
        cookieAuthorization.setMaxAge(60);
        httpServletResponse.addCookie(cookieAuthorization);

        Cookie cookieClient = new Cookie("Client", String.valueOf(userService.checkAuthorization(user.getLogin(), user.getPassword()).getId()));
        cookieClient.setMaxAge(60);
        httpServletResponse.addCookie(cookieClient);
        return "redirect:/setting"; // и переходим в лк (в раздел настройки)
    }

    @GetMapping("/authorization")
    public String authorizationPage(@ModelAttribute("user") User user,
                                    @CookieValue(value = "Authorization", required = false) String authorization,
                                    @CookieValue(value = "Client", required = false) String client) {

        if (authorization != null) {
            if (authorization.equals("true")) return "main/exit";
            // показать страницу статистики магазина, используя куки
            else {
                return "auth/authorization";
            }
        } else {
            return "auth/authorization";
        }
    }

    @PostMapping("/authorization")
    public String authorizationUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                    HttpServletResponse httpServletResponse) {
        userValidatorAuthorization.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/authorization";

        Cookie cookieAuthorization = new Cookie("Authorization", "true");
        cookieAuthorization.setMaxAge(60);
        httpServletResponse.addCookie(cookieAuthorization);

        Cookie cookieClient = new Cookie("Client", String.valueOf(userService.checkAuthorization(user.getLogin(), user.getPassword()).getId()));
        cookieClient.setMaxAge(60);
        httpServletResponse.addCookie(cookieClient);

        return "redirect:/exit"; // и переходим в лк
    }


}
