package ru.sokolov.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sokolov.com.Auth;
import ru.sokolov.models.User;
import ru.sokolov.services.UserService;
import ru.sokolov.util.UserValidatorAuthentication;
import ru.sokolov.util.UserValidatorAuthorization;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

        if (!Auth.authorization(authorization, client)) return "auth/authentication";

        return "redirect:/account/settings";
    }

    @PostMapping()
    public String authenticationUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                     HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse) {
        userValidatorAuthentication.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/authentication";

        userService.save(user); // если данные формы введены корректно, то сохраняем пользователя
        // получаем уникальный номер

        System.out.println(httpServletRequest.getHeaderNames());

        Cookie cookieAuthorization = new Cookie("Authorization", "true");
        cookieAuthorization.setMaxAge(600);
        cookieAuthorization.setSecure(true);
        cookieAuthorization.setHttpOnly(true);
        cookieAuthorization.setPath("/");
        httpServletResponse.addCookie(cookieAuthorization);

        Cookie cookieClient = new Cookie("Client", String.valueOf(userService.checkAuthorization(user.getEmail(), user.getPassword()).getId()));
        cookieClient.setMaxAge(600);
        cookieClient.setSecure(true);
        cookieClient.setHttpOnly(true);
        cookieClient.setPath("/");
        httpServletResponse.addCookie(cookieClient);

//        Cookie cookieUniqueIdentificator = new Cookie("UniqueIdentificator", String.valueOf(userService.checkAuthorization(user.getEmail(), user.getPassword()).getId()));
//        cookieUniqueIdentificator.setMaxAge(60);
//        cookieUniqueIdentificator.setSecure(true);
//        cookieUniqueIdentificator.setHttpOnly(true);
//        cookieUniqueIdentificator.setPath("/");
//        httpServletResponse.addCookie(cookieUniqueIdentificator);
        return "redirect:/account/settings"; // и переходим в лк (в раздел настройки)
    }

    @GetMapping("/authorization")
    public String authorizationPage(@ModelAttribute("user") User user,
                                    @CookieValue(value = "Authorization", required = false) String authorization,
                                    @CookieValue(value = "Client", required = false) String client) {

        if (!Auth.authorization(authorization, client)) return "auth/authorization";

        return "redirect:/account/settings";
    }

    @PostMapping("/authorization")
    public String authorizationUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                    HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse) {
        userValidatorAuthorization.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/authorization";

        System.out.println(httpServletRequest.getHeader("Sec-Ch-Ua"));
        System.out.println(httpServletRequest.getHeader("Sec-Ch-Ua-Platform"));

        Cookie cookieAuthorization = new Cookie("Authorization", "true");
        cookieAuthorization.setMaxAge(600);
        cookieAuthorization.setSecure(true);
        cookieAuthorization.setHttpOnly(true);
        cookieAuthorization.setPath("/");
        httpServletResponse.addCookie(cookieAuthorization);

        Cookie cookieClient = new Cookie("Client", String.valueOf(userService.checkAuthorization(user.getEmail(), user.getPassword()).getId()));
        cookieClient.setMaxAge(600);
        cookieClient.setSecure(true);
        cookieClient.setHttpOnly(true);
        cookieClient.setPath("/");
        httpServletResponse.addCookie(cookieClient);

        return "redirect:/account/settings"; // и переходим в лк
    }


}
