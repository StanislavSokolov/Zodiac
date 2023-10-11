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
            return "main/Статистика";
    }
}
