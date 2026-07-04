package learning.springboot.JpaMapping.service;

import learning.springboot.JpaMapping.dto.CourseRequestDTO;
import learning.springboot.JpaMapping.dto.CourseResponseDTO;
import learning.springboot.JpaMapping.exception.BadRequestException;
import learning.springboot.JpaMapping.exception.ResourceNotFoundException;
import learning.springboot.JpaMapping.mapper.CourseMapper;
import learning.springboot.JpaMapping.model.Course;
import learning.springboot.JpaMapping.repository.CourseRepo;
import learning.springboot.JpaMapping.util.CourseStudentCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepo repo;
    private final CourseMapper mapper;

    public void addMock() {
        List<Course> courses = List.of(
                new Course("Java Programming", "java", "Sunil Diwakar", 3),
                new Course("Python for AI & ML", "python", "Ajay Manothra", 4),
                new Course("Spring Boot Microservices", "springboot", "Sharmila Banu", 7),
                new Course("Data Structures & Algorithms", "dsa", "Lily", 10),
                new Course("Full Stack Development (React + Spring Boot)", "fullstack", "Vikram Rao", 8),
                new Course("DevOps with Docker & Kubernetes", "devops", "Michael Chen", 6),
                new Course("AWS Cloud Practitioner", "aws", "Ananya Sharma", 5),
                new Course("Cyber Security Basics", "cybersecurity", "David John", 4),
                new Course("Data Science with Python", "datascience", "Neha Patel", 6),
                new Course("System Design Fundamentals", "systemdesign", "Karthik Subramanian", 12)
        );

        repo.saveAll(courses);
    }

    public CourseResponseDTO addCourse(CourseRequestDTO req) {

        if (repo.existsByCode(req.getCode())) {
            throw new BadRequestException(
                    "Course already exists with code: " + req.getCode()
            );
        }

        Course course = mapper.toCourseEntity(req);

        Course savedCourse = repo.save(course);

        return mapper.toCourseResponseDTO(savedCourse);
    }

    public List<CourseResponseDTO> getAll() {
        List<Course> courses = repo.findAll();

        if (courses.isEmpty()) {
            throw new ResourceNotFoundException("Courses data is empty");
        }

        return courses.stream()
                .map(mapper::toCourseResponseDTO)
                .toList();
    }

    public CourseResponseDTO getOne(String code) {
        Course course = repo.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with code: " + code
                ));

        return mapper.toCourseResponseDTO(course);
    }

    public CourseResponseDTO updateOne(String code, CourseRequestDTO req) {

        Course course = repo.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with code: " + code
                ));

        if (!code.equals(req.getCode()) && repo.existsByCode(req.getCode())) {
            throw new BadRequestException(
                    "Course already exists with code: " + req.getCode()
            );
        }

        course.setCode(req.getCode());
        course.setTitle(req.getTitle());
        course.setStaff(req.getStaff());
        course.setDuration(req.getDuration());

        Course updatedCourse = repo.save(course);

        return mapper.toCourseResponseDTO(updatedCourse);
    }

    @Transactional
    public void deleteOne(String code) {

        Course course = repo.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with code: " + code
                ));

        /*
         * Business rule:
         * Course delete pannumbothu, andha course la enrolled students irundha,
         * student.course_id ah NULL pannuvom.
         * Then course delete pannuvom.
         */
        repo.removeCourseFromStudents(course.getCourse_id());

        repo.delete(course);
    }

    public List<CourseStudentCount> getSummary() {
        return repo.getCourseWiseStudentCount();
    }

    public long getStudentCountByCourse(String code) {
        repo.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with code: " + code
                ));

        return repo.countStudentsByCourseCode(code);
    }
}