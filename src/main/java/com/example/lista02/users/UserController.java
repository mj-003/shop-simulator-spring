package com.example.lista02.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        System.out.println("Registering user: " + user.getUsername());
        try {
            userService.createUser(user.getUsername(), user.getPassword(), "ROLE_USER");
            System.out.println("User created" + user.getUsername());
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
    }
}
