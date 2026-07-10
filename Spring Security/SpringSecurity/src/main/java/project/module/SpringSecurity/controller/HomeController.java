package project.module.SpringSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.module.SpringSecurity.entity.Users;
import project.module.SpringSecurity.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users-mgmt")
public class HomeController {

    @Autowired
    private UserService usersService;

    // Create
    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return usersService.saveUser(user);
    }

    // Read All
    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    // Read By Id
    @GetMapping("/{id}")
    public Optional<Users> getUser(@PathVariable Integer id) {
        return usersService.getUserById(id);
    }

    // Update
    @PutMapping("/{id}")
    public Users updateUser(@PathVariable Integer id,
                            @RequestBody Users user) {
        return usersService.updateUser(id, user);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        return usersService.deleteUser(id);
    }
}