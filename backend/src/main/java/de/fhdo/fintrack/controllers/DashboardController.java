package de.fhdo.fintrack.controllers;

import de.fhdo.fintrack.entities.User;
import de.fhdo.fintrack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getDashboard(Model model) {
        Long userId = 1L; // Replace with dynamic user ID from session/authentication
        User user = userService.getUserById(userId);

        model.addAttribute("user", user);
        return "dashboard";
    }
}
