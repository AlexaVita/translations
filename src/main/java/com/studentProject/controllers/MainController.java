package com.studentProject.controllers;

import com.studentProject.entities.Role;
import com.studentProject.entities.User;
import com.studentProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

//Отвечает за реализацию аутентификации и авторизации.
@Controller
public class MainController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginPage(@AuthenticationPrincipal User user) {

        if (user != null) {
            return "redirect:/chapters";
        }

        return "login";
    }


    @GetMapping("/registration")
    public String getRegistrationPage(@AuthenticationPrincipal User user,
                                      Model model
    ) {

        if (user != null) {
            return "redirect:/chapters";
        }

        model.addAttribute("user", new User());

        return "/registration";
    }

    @PostMapping("/registration")
    public String postRegistrationPage(@AuthenticationPrincipal @ModelAttribute("user") @Valid User user,
                                       BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User dbUser = userRepository.findByUsername(user.getUsername());

        if (dbUser != null) {
            return "redirect:/chapters";
        }

        String password1 = passwordEncoder.encode(user.getPassword());
        User newUser = new User(user.getUsername(), password1, true, Role.USER);
        userRepository.save(newUser);

        return "redirect:/login";
    }

    @GetMapping("/")
    public String mainPage() {
        return "redirect:/chapters";
    }
}
