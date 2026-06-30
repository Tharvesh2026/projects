package projects.icore.CoursePortal.dto;

import java.util.List;

public record StudentProfileResponse(
        Integer rollNo,
        String name,
        String phone,
        List<CourseSummaryResponse> courses,
        long enrollmentCount
) {
}