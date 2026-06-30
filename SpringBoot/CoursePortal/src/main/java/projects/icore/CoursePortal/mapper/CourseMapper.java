package projects.icore.CoursePortal.mapper;

import org.springframework.stereotype.Component;
import projects.icore.CoursePortal.dto.CourseRequest;
import projects.icore.CoursePortal.dto.CourseResponse;
import projects.icore.CoursePortal.dto.CourseSummaryResponse;
import projects.icore.CoursePortal.entity.Course;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequest request) {
        if (request == null) {
            return null;
        }
        return Course.builder()
                .code(request.code())
                .name(request.name())
                .description(request.description())
                .credits(request.credits())
                .build();
    }

    public CourseResponse toResponse(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseResponse(
                course.getCode(),
                course.getName(),
                course.getDescription(),
                course.getCredits()
        );
    }

    public CourseSummaryResponse toSummary(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseSummaryResponse(
                course.getCode(),
                course.getName()
        );
    }
}
