package ru.sokolov.controllers;

import jakarta.validation.Valid;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sokolov.com.Auth;
import ru.sokolov.com.Define;
import ru.sokolov.models.User;
import ru.sokolov.models.UserAuth;
import ru.sokolov.services.UserAuthService;
import ru.sokolov.services.UserService;
import ru.sokolov.util.UserValidatorAuthentication;
import ru.sokolov.util.UserValidatorAuthorization;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final UserService userService;
    private final UserAuthService userAuthService;
    private final UserValidatorAuthentication userValidatorAuthentication;
    private final UserValidatorAuthorization userValidatorAuthorization;

    public AuthController(UserService userService, UserAuthService userAuthService, UserValidatorAuthentication userValidatorAuthentication, UserValidatorAuthorization userValidatorAuthorization) {
        this.userService = userService;
        this.userAuthService = userAuthService;
        this.userValidatorAuthentication = userValidatorAuthentication;
        this.userValidatorAuthorization = userValidatorAuthorization;
    }

    @GetMapping
    public String authenticationPage(@ModelAttribute("user") User user,
                                     HttpServletRequest httpServletRequest,
                                     @CookieValue(value = "Eq5__4tJHe", required = false) String authorization,
                                     @CookieValue(value = "mMmQ-12_1e2", required = false) String client) {

        List<UserAuth> userAuths = userAuthService.findByAuthorizationAndDeviceAndIp(authorization, httpServletRequest.getRemoteAddr(), httpServletRequest.getHeader("User-Agent"));
        if (userAuths == null)
            return "auth/authentication";

        if (userAuths.get(0).getOwner().getId() == Integer.parseInt(client))
            return "redirect:/account/settings";

        return "auth/authentication";
    }

    @PostMapping()
    public String authenticationUser(@ModelAttribute("user") @Valid User usr, BindingResult bindingResult,
                                     HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse) {
        userValidatorAuthentication.validate(usr, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/authentication";

        User user = userService.save(usr);
        String s = RandomString.make(25);
        UserAuth userAuth = new UserAuth(s, httpServletRequest.getRemoteAddr(), httpServletRequest.getHeader("User-Agent"), user);
        userAuthService.save(userAuth);

        // Всё надо проверять, нельзя засовывать это в базу.

        Cookie cookieAuthorization = new Cookie("Eq5__4tJHe", s);
        cookieAuthorization.setMaxAge(Define.getCookieMaxAge());
        cookieAuthorization.setSecure(true);
        cookieAuthorization.setHttpOnly(true);
        cookieAuthorization.setPath("/");
        httpServletResponse.addCookie(cookieAuthorization);

        Cookie cookieClient = new Cookie("mMmQ-12_1e2", String.valueOf(user.getId()));
        cookieClient.setMaxAge(Define.getCookieMaxAge());
        cookieClient.setSecure(true);
        cookieClient.setHttpOnly(true);
        cookieClient.setPath("/");
        httpServletResponse.addCookie(cookieClient);

        return "redirect:/account/settings"; // и переходим в лк (в раздел настройки)
    }

    @GetMapping("/authorization")
    public String authorizationPage(@ModelAttribute("user") User user,
                                    HttpServletRequest httpServletRequest,
                                    @CookieValue(value = "Eq5__4tJHe", required = false) String authorization,
                                    @CookieValue(value = "mMmQ-12_1e2", required = false) String client) {

        List<UserAuth> userAuths = userAuthService.findByAuthorizationAndDeviceAndIp(authorization, httpServletRequest.getRemoteAddr(), httpServletRequest.getHeader("User-Agent"));
        if (userAuths == null)
            return "auth/authorization";

        if (userAuths.get(0).getOwner().getId() == Integer.parseInt(client))
            return "redirect:/account/settings";

        return "auth/authorization";
    }

    @PostMapping("/authorization")
    public String authorizationUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                    HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse) {
        userValidatorAuthorization.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/authorization";

        User u = userService.checkAuthorization(user.getEmail(), user.getPassword());


        String s = RandomString.make(25);

        Cookie cookieAuthorization = new Cookie("Authorization", s);
        cookieAuthorization.setMaxAge(Define.getCookieMaxAge());
        cookieAuthorization.setSecure(true);
        cookieAuthorization.setHttpOnly(true);
        cookieAuthorization.setPath("/");
        httpServletResponse.addCookie(cookieAuthorization);

        return "redirect:/account/settings"; // и переходим в лк
    }


}
