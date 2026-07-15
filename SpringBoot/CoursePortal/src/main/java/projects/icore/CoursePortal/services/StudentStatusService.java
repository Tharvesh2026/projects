package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentStatusService {

    private final CourseRecommendationService courseRecommendationService;

    public String getStudentStatus(Integer rollNo) {

        int recommendedCourseCount =
                courseRecommendationService.getRecommendedCourseCount(rollNo);

        if (recommendedCourseCount > 0) {
            return "ACTIVE";
        }

        return "NEW";
    }
}