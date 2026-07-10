package project.module.SpringSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.module.SpringSecurity.entity.Users;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String name);
    Optional<Users> findByEmail(String email);

}
