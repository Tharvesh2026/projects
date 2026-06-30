package projects.icore.CoursePortal.mapper;

import org.springframework.stereotype.Component;
import projects.icore.CoursePortal.dto.StudentRequest;
import projects.icore.CoursePortal.dto.StudentResponse;
import projects.icore.CoursePortal.dto.StudentSummaryResponse;
import projects.icore.CoursePortal.entity.Student;

@Component
public class StudentMapper {

    public Student toEntity(StudentRequest request) {
        if (request == null) {
            return null;
        }
        return Student.builder()
                .rollNo(request.rollNo())
                .name(request.name())
                .phone(request.phone())
                .build();
    }

    public StudentResponse toResponse(Student student) {
        if (student == null) {
            return null;
        }
        return new StudentResponse(
                student.getRollNo(),
                student.getName(),
                student.getPhone()
        );
    }

    public StudentSummaryResponse toSummary(Student student) {
        if (student == null) {
            return null;
        }
        return new StudentSummaryResponse(
                student.getRollNo(),
                student.getName()
        );
    }
}
