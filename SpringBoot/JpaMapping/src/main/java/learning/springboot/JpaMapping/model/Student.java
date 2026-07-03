package learning.springboot.JpaMapping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Student {
    private String name;
    private String gender;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rollNo;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
