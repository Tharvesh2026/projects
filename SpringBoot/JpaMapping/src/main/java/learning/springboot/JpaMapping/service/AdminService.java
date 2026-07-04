package learning.springboot.JpaMapping.service;

import learning.springboot.JpaMapping.exception.BadRequestException;
import learning.springboot.JpaMapping.repository.CourseRepo;
import learning.springboot.JpaMapping.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;

    private static final String ADMIN_PASSWORD = "cgy7EZbcWR6p";

    @Transactional
    public String flushDatabase(String password, String entity) {

        if (!ADMIN_PASSWORD.equals(password)) {
            throw new BadRequestException("Invalid admin password");
        }

        if (entity == null || entity.isBlank()) {
            throw new BadRequestException("Entity value is required");
        }

        String normalizedEntity = entity.toLowerCase();

        switch (normalizedEntity) {
            case "students" -> {
                studentRepo.deleteAll();
                return "All students deleted successfully";
            }

            case "courses" -> {
                if (studentRepo.count() > 0) {
                    throw new BadRequestException(
                            "Cannot delete courses because students are still registered. Flush students first or use entity=all"
                    );
                }

                courseRepo.deleteAll();
                return "All courses deleted successfully";
            }

            case "all" -> {
                studentRepo.deleteAll();
                courseRepo.deleteAll();
                return "All students and courses deleted successfully";
            }

            default -> throw new BadRequestException(
                    "Invalid entity value. Allowed values: students, courses, all"
            );
        }
    }
}