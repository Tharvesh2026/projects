package project.module.SpringSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.module.SpringSecurity.entity.Users;
import project.module.SpringSecurity.repository.UsersRepo;

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
    public Page<Users> getAllUsers(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return usersRepo.findAll(pageable);
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