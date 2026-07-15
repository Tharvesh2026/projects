package project.module.SpringSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.module.SpringSecurity.entity.Users;

@Repository
public interface UserDetailRepo extends JpaRepository<Users, Long> {
    Users getByName(String name);
}
