package learning.springboot.JpaMapping.service;

import learning.springboot.JpaMapping.dto.StudentRequestDTO;
import learning.springboot.JpaMapping.dto.StudentResponseDTO;
import learning.springboot.JpaMapping.exception.ResourceNotFoundException;
import learning.springboot.JpaMapping.mapper.StudentMapper;
import learning.springboot.JpaMapping.model.Course;
import learning.springboot.JpaMapping.model.Student;
import learning.springboot.JpaMapping.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo repo;
    private final StudentMapper studentMapper;
    private final ValidationQueryService queryService;

    public StudentResponseDTO createOne(StudentRequestDTO req){
        Course course = queryService.getCourseOrThrow(req.getCourseId());
        Student student = studentMapper.toStudentEntity(req,course);
        Student save = repo.save(student);

        return studentMapper.toStudentResponseDTO(save);

    }

    public List<StudentResponseDTO> getAllStudents() {

        List<Student> students = repo.findAll();

        if(students.isEmpty()){
            throw new ResourceNotFoundException("students data is empty");
        }

        return students.stream()
                .map(studentMapper::toStudentResponseDTO)
                .toList();
    }

    public StudentResponseDTO getOne(Integer id){
        Student student = repo.findById(id).orElseThrow(()->new ResourceNotFoundException("student not found with roll no: "+id));
        return studentMapper.toStudentResponseDTO(student);

    }

    public void deleteOne(Integer id){
        repo.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Student Not found with rollNo: "+id));
        repo.deleteById(id);
    }


    public StudentResponseDTO updateOne(Integer rollNo, StudentRequestDTO req) {
        Student existingStudent = repo.findById(rollNo).
                orElseThrow(() -> new ResourceNotFoundException("Student not found with rollNo: " + rollNo));

        Course course = queryService.getCourseOrThrow(req.getCourseId());

        existingStudent.setName(req.getName());
        existingStudent.setGender(req.getGender());
        existingStudent.setCourse(course);

        Student updatedStudent = repo.save(existingStudent);

        return studentMapper.toStudentResponseDTO(updatedStudent);
    }
}