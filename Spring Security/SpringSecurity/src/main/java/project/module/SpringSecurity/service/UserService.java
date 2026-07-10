package project.module.SpringSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.module.SpringSecurity.entity.Users;
import project.module.SpringSecurity.repository.UsersRepo;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersRepo usersRepo;

    // Create
    public Users saveUser(Users user) {
        return usersRepo.save(user);
    }

    // Read All
    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    // Read By Id
    public Optional<Users> getUserById(Integer id) {
        return usersRepo.findById(id);
    }

    // Update
    public Users updateUser(Integer id, Users user) {
        Users existingUser = usersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return usersRepo.save(existingUser);
    }

    // Delete
    public String deleteUser(Integer id) {
        usersRepo.deleteById(id);
        return "User deleted successfully";
    }
}