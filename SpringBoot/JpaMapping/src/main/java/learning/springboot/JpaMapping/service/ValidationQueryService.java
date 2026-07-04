package learning.springboot.JpaMapping.service;

import learning.springboot.JpaMapping.dto.StudentRequestDTO;
import learning.springboot.JpaMapping.exception.BadRequestException;
import learning.springboot.JpaMapping.exception.ResourceNotFoundException;
import learning.springboot.JpaMapping.model.Course;
import learning.springboot.JpaMapping.model.Student;
import learning.springboot.JpaMapping.util.CourseStudentCount;
import learning.springboot.JpaMapping.repository.CourseRepo;
import learning.springboot.JpaMapping.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationQueryService  {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;

    public Course getCourseOrThrow(int courseId) {
        return courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + courseId
                ));
    }

    public void checkExistOrThrow(String code) {
        if(courseRepo.findByCode(code).isPresent()){
            throw new BadRequestException("course already exist with code"+code);
        }
    }

    public List<CourseStudentCount> getCourseWiseStudentCount() {
        return courseRepo.getCourseWiseStudentCount();
    }
}