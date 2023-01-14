package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;
    private final RoleRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public AdminController(UserService service, RoleRepository repository, RoleRepository repository1, PasswordEncoder encoder) {
        this.service = service;
        this.repository = repository1;
        this.encoder = encoder;
    }

    @GetMapping("/panel")
    public String test(Model model,@AuthenticationPrincipal User currentUser) {
        List<User> allUsers = service.getAllUsers();
        model.addAttribute("allUsers",allUsers);
        model.addAttribute("user",currentUser);
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles",repository.findAll());
        return "admin";
    }
//    @GetMapping(value = "/all")
//    public String showAllUsers(Model model) {
//        List<User> allUsers = service.getAllUsers();
//        model.addAttribute("allUsers",allUsers);
//        return "all-Users";
//    }


//    @GetMapping(value = "/new")
//    public String newUser(Model model) {
//        User user = new User();
//        user.addRole(new Role());
//        user.addRole(new Role());
//        model.addAttribute("user",user);
//        return "add-User";
//    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        List<Role> roleList = user.getRoles();
        Optional<Role> roleUser = repository.findById(1);
        if (roleList.isEmpty()) {
            roleList.add(roleUser.get());
        } else if (roleList.get(0).getRole()=="ROLE_ADMIN") {
            roleList.add(roleUser.get());
        }
        user.setRoles(roleList);
        service.saveUser(user);
        return "redirect:/admin/panel";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("userEdit",service.getUserById(id));
        return "admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("editUser") User user,
                         @PathVariable("id") int id) {
        List<Role> roleList = user.getRoles();
        Optional<Role> roleUser = repository.findById(1);
        Optional<Role> roleAdmin = repository.findById(2);
        if (roleList.isEmpty()) {
            roleList.add(roleUser.get());
        } else if (roleList.get(0).getRole()=="ROLE_ADMIN") {
            roleList.add(roleUser.get());
        }
        if (roleList.get(0).getRole() == "1") {
            roleList.add(roleUser.get());
        } else if (roleList.get(0).getRole() == "2") {
            roleList.add(roleAdmin.get());
        }
        user.setRoles(roleList);
        user.setPassword(encoder.encode(user.getPassword()));
        service.updateUser(id,user);
        return "redirect:/admin/panel";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("user") User user,
                         @PathVariable("id") int id) {
        service.deleteUser(id);
        return "redirect:/admin/panel";
    }
    @GetMapping("/test")
    public String testtest(Model model,@AuthenticationPrincipal User currentUser) {
        model.addAttribute("user",currentUser);
        return "test";
    }
}
