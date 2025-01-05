package de.fhdo.fintrack.controllers;

import de.fhdo.fintrack.entities.User;
import de.fhdo.fintrack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    // Render the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Thymeleaf template name
    }

    // Handle login form submission
    @PostMapping("/login")
    public String processLogin(
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        try {
            User user = userService.authenticateUser(email, password);
            model.addAttribute("user", user);
            return "dashboard"; // Redirect to dashboard upon successful login
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login"; // Stay on login page if authentication fails
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("logout", "You have been successfully logged out.");
        return "login";
    }
}

