package ru.sokolov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String mainPage(@CookieValue(value = "Authentication", required = false) String authentication, HttpServletResponse httpServletResponse, Model model) {

        if (authentication != null) {
            if (authentication.equals("true")) return "main/start";
            else {
                model.addAttribute("message", "Hello,  MainController");
                Cookie cookie = new Cookie("Authentication", "true");
                cookie.setMaxAge(60);
                httpServletResponse.addCookie(cookie);
                return "first/hello";
            }
        } else {
            return "main/Статистика";
        }

    }
}
