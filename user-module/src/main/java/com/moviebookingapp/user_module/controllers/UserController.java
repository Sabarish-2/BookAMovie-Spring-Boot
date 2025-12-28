package com.moviebookingapp.user_module.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("log")
    public String login() {
        return "Public";
    }
    @GetMapping("admin")
    public String admin() {
        return "private";
    }
}
