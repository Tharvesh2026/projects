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
    private final EnrollmentService enrollmentService;

     public Student register(Student student){
         log.info("Student Created {}", student.getRollNo());
         log.info("Available Courses {}",portalStatsService.getTotalCourses());
         return studentRepo.save(student);
     }

     public Student getById(Integer id){
         return studentRepo.findById(id).orElseThrow(()->new RuntimeException("Student Not Found"));
     }

     public List<Student> getAll(){
         return studentRepo.findAll();
     }

     public boolean checkIdExists(Integer id){
         return studentRepo.existsById(id);
     }

     public long getStudentEnrollmentCount(Integer rollNo){
        return enrollmentService.getCoursesByStudent(rollNo).size();
     }

}
