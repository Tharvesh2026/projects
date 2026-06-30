package projects.icore.CoursePortal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    private Integer rollNo;

    private String name;
    private String phone;
}
