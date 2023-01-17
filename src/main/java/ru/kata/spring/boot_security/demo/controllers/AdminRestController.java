package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> allUsers = userService.findAll();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        userService.saveOrUpdateUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        userService.saveOrUpdateUser(user);
        return ResponseEntity.ok(user);
    }
}
