package learning.springboot.JpaMapping.service;

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
public class EnrollmentService {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;

    public Student enrollStudentToCourse(int rollNo, String courseCode) {

        Student student = studentRepo.findById(rollNo)
                .orElseThrow(() -> new RuntimeException("Student not found with rollNo: " + rollNo));

        Course course = courseRepo.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found with code: " + courseCode));

        student.setCourse(course);

        return studentRepo.save(student);
    }

    public List<CourseStudentCount> getCourseWiseStudentCount() {
        return courseRepo.getCourseWiseStudentCount();
    }
}