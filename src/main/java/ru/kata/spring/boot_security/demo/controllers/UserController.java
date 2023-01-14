package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService service;
    private final PasswordEncoder encoder;
    private final RoleRepository repository;


    @Autowired
    public UserController(UserService service, PasswordEncoder encoder, RoleRepository repository) {
        this.service = service;
        this.encoder = encoder;
        this.repository = repository;
    }




    @GetMapping("/info")
    public String edit(Model model, Principal principal) {
        model.addAttribute("user", service.loadUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/panel")
    public String test(Model model,@AuthenticationPrincipal User currentUser) {
        model.addAttribute("user",currentUser);
        return "user";
    }
}
