package learning.springboot.JpaMapping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {

    private int course_id;
    private String code;
    private String title;
    private String staff;
    private int duration;
}