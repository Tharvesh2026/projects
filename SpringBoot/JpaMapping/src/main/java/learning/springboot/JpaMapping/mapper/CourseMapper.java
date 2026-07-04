package learning.springboot.JpaMapping.mapper;

import learning.springboot.JpaMapping.dto.CourseRequestDTO;
import learning.springboot.JpaMapping.dto.CourseResponseDTO;
import learning.springboot.JpaMapping.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toCourseEntity(CourseRequestDTO req) {
        return Course.builder()
                .code(req.getCode())
                .title(req.getTitle())
                .staff(req.getStaff())
                .duration(req.getDuration())
                .build();
    }

    public CourseResponseDTO toCourseResponseDTO(Course course) {
        return new CourseResponseDTO(
                course.getCourse_id(),
                course.getCode(),
                course.getTitle(),
                course.getStaff(),
                course.getDuration()
        );
    }
}
