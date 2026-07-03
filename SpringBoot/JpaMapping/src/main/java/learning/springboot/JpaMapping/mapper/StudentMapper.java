package learning.springboot.JpaMapping.mapper;

import learning.springboot.JpaMapping.dto.CourseInfoDTO;
import learning.springboot.JpaMapping.dto.StudentResponseDTO;
import learning.springboot.JpaMapping.model.Course;
import learning.springboot.JpaMapping.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentResponseDTO toStudentResponseDTO(Student student) {

        Course course = student.getCourse();

        CourseInfoDTO courseInfo = null;

        if (course != null) {
            courseInfo = new CourseInfoDTO(
                    course.getCourse_id(),
                    course.getCode(),
                    course.getTitle(),
                    course.getStaff(),
                    course.getDuration()
            );
        }

        return new StudentResponseDTO(
                student.getRollNo(),
                student.getName(),
                student.getGender(),
                courseInfo
        );
    }
}