package learning.springboot.JpaMapping.mapper;

import learning.springboot.JpaMapping.dto.CourseResponseDTO;
import learning.springboot.JpaMapping.dto.StudentRequestDTO;
import learning.springboot.JpaMapping.dto.StudentResponseDTO;
import learning.springboot.JpaMapping.model.Course;
import learning.springboot.JpaMapping.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentResponseDTO toStudentResponseDTO(Student student) {

        Course course = student.getCourse();

        CourseResponseDTO courseInfo = null;

        if (course != null) {
            courseInfo = new CourseResponseDTO(
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

    public Student toStudentEntity(StudentRequestDTO dto, Course course) {
        return Student.builder()
                .name(dto.getName())
                .gender(dto.getGender())
                .course(course)
                .build();
    }

}