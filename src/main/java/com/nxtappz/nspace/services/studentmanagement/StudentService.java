package com.nxtappz.nspace.services.studentmanagement;

import com.nxtappz.nspace.domain.studentmanagement.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    Student insertStudent(Student student);

    Student updateStudent(Student student);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    Student getOrCreateStudent(Student student);
}
