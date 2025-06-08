package br.com.animetracker.AniTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.animetracker.AniTracker.model.User;
import br.com.animetracker.AniTracker.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute("user") User user,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (user.getPassword().length() < 8) {
            model.addAttribute("passwordError", "Password must be at least 8 characters long");
            return "register";
        }

        if (userService.isUsernameTaken(user.getUsername())) {
            model.addAttribute("usernameError", "Username is already taken");
            return "register";
        }

        if (userService.isEmailRegistered(user.getEmail())) {
            model.addAttribute("emailError", "Email is already registered");
            return "register";
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        User registeredUser = userService.registerUser(
                user.getUsername(),
                encodedPassword,
                user.getEmail()
        );

        if (registeredUser == null) {
            model.addAttribute("registrationError", "Registration failed");
            return "register";
        }

        return "redirect:/login?registered";
    }
}
