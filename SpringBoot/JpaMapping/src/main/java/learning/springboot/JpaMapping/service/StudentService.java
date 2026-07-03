package learning.springboot.JpaMapping.service;

import learning.springboot.JpaMapping.dto.StudentResponseDTO;
import learning.springboot.JpaMapping.mapper.StudentMapper;
import learning.springboot.JpaMapping.model.Student;
import learning.springboot.JpaMapping.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo repo;
    private final StudentMapper studentMapper;

    public List<StudentResponseDTO> getAllStudents() {

        List<Student> students = repo.findAll();

        if(students.isEmpty()){
            throw new ResourceAccessException("students data is empty");
        }

        return students.stream()
                .map(studentMapper::toStudentResponseDTO)
                .toList();
    }

    public StudentResponseDTO getOne(Integer id){
        Student student = repo.findById(id).orElseThrow(()->new ResourceAccessException("student not found with roll no: "+id));
        return studentMapper.toStudentResponseDTO(student);

    }
}