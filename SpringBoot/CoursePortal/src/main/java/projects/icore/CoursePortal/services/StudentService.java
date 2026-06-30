package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.entity.Student;
import projects.icore.CoursePortal.repository.StudentRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepo studentRepo;
    private final PortalStatsService portalStatsService;

    public Student register(Student student) {

        log.info("Registering student. RollNo={}, Name={}",
                student.getRollNo(), student.getName());

        log.info("Available courses={}",
                portalStatsService.getTotalCourses());

        Student savedStudent = studentRepo.save(student);

        log.info("Student registered successfully. RollNo={}",
                savedStudent.getRollNo());

        return savedStudent;
    }

    public Student getById(Integer id) {

        log.info("Fetching student. RollNo={}", id);

        Student student = studentRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found. RollNo={}", id);
                    return new RuntimeException("Student Not Found");
                });

        log.info("Student found. RollNo={}, Name={}",
                student.getRollNo(), student.getName());

        return student;
    }

    public List<Student> getAll() {

        log.info("Fetching all students");

        List<Student> students = studentRepo.findAll();

        log.info("Total students={}", students.size());

        return students;
    }

    public boolean checkIdExists(Integer id) {

        boolean exists = studentRepo.existsById(id);

        log.debug("Student exists check. RollNo={}, Exists={}",
                id, exists);

        return exists;
    }

    
}

