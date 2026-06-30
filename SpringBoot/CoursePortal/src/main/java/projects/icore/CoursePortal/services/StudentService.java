package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.dto.StudentRequest;
import projects.icore.CoursePortal.dto.StudentResponse;
import projects.icore.CoursePortal.entity.Student;
import projects.icore.CoursePortal.exception.ResourceNotFoundException;
import projects.icore.CoursePortal.mapper.StudentMapper;
import projects.icore.CoursePortal.repository.StudentRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepo studentRepo;
    private final PortalStatsService portalStatsService;
    private final StudentMapper studentMapper;

    public StudentResponse register(StudentRequest request) {
        log.info("Registering student. RollNo={}, Name={}",
                request.rollNo(), request.name());

        log.info("Available courses={}",
                portalStatsService.getTotalCourses());

        Student student = studentMapper.toEntity(request);
        Student savedStudent = studentRepo.save(student);

        log.info("Student registered successfully. RollNo={}",
                savedStudent.getRollNo());

        return studentMapper.toResponse(savedStudent);
    }

    public StudentResponse getById(Integer id) {
        log.info("Fetching student. RollNo={}", id);

        Student student = studentRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found. RollNo={}", id);
                    return new ResourceNotFoundException("Student Not Found");
                });

        log.info("Student found. RollNo={}, Name={}",
                student.getRollNo(), student.getName());

        return studentMapper.toResponse(student);
    }

    public List<StudentResponse> getAll() {
        log.info("Fetching all students");

        List<Student> students = studentRepo.findAll();

        log.info("Total students={}", students.size());

        return students.stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public boolean checkIdExists(Integer id) {
        boolean exists = studentRepo.existsById(id);
        log.debug("Student exists check. RollNo={}, Exists={}",
                id, exists);
        return exists;
    }
}
