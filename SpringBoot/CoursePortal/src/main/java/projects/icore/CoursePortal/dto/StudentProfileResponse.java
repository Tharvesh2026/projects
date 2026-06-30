package projects.icore.CoursePortal.dto;

import projects.icore.CoursePortal.entity.Course;
import projects.icore.CoursePortal.entity.Student;

import java.util.List;

public record StudentProfileResponse(
        Student student,
        List<Course> courses,
        long enrollmentCount
) {
}