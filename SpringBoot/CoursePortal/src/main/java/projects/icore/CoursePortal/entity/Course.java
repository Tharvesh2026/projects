package projects.icore.CoursePortal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    private String code;

    private String name;
    private String description;
    private int credits;
}
