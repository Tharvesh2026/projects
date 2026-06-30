package projects.icore.CoursePortal.mapper;

import org.springframework.stereotype.Component;
import projects.icore.CoursePortal.dto.EnrollmentResponse;
import projects.icore.CoursePortal.entity.Enrollment;

@Component
public class EnrollmentMapper {

    public EnrollmentResponse toResponse(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getRollNo(),
                enrollment.getCourseCode()
        );
    }
}
