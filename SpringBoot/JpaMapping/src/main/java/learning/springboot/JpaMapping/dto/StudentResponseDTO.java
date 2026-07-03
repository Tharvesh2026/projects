package learning.springboot.JpaMapping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class StudentResponseDTO {
    private int rollNo;
    private String name;
    private String gender;
    private CourseInfoDTO course;
}
