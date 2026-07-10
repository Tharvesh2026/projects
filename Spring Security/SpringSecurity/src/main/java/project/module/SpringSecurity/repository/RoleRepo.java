package project.module.SpringSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.module.SpringSecurity.entity.Role;
import project.module.SpringSecurity.entity.Users;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
