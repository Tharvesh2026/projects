package project.module.SpringSecurity.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor

public class Role {
    private Integer id;
    private String name;
}
