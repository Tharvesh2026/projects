package learning.springboot.JpaMapping.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long course_id;
    private String title;
    private String code;
    private String staff;
    private int duration;

    @OneToMany(mappedBy = "course")
    private List<Student> students = new ArrayList<>();

    public Course(String title, String code, String staff, int duration) {
        this.title = title;
        this.code = code;
        this.staff = staff;
        this.duration = duration;
    }

}
