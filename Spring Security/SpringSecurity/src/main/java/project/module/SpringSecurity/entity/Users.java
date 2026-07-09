package project.module.SpringSecurity.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;
    private String password;

    @ManyToOne()
    private Role role;
    private String dept;
}
