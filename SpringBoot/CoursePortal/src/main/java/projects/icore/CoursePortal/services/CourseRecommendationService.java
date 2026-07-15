package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.dto.StudentProfileResponse;

@Service
@RequiredArgsConstructor
public class CourseRecommendationService {

    private final StudentProfileService studentProfileService;

    public int getRecommendedCourseCount(Integer rollNo) {

        StudentProfileResponse profile = studentProfileService.getProfile(rollNo);

        if (profile.enrollmentCount() == 0) {
            return 3;
        }

        return 1;
    }
}