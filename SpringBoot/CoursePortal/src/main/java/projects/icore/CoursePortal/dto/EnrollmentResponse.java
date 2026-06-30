package projects.icore.CoursePortal.dto;

public record EnrollmentResponse(
        Long id,
        Integer rollNo,
        String courseCode
) {
}