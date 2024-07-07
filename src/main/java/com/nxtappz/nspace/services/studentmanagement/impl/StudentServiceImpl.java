package com.nxtappz.nspace.services.studentmanagement.impl;

import com.nxtappz.nspace.domain.studentmanagement.Student;
import com.nxtappz.nspace.exceptions.BusinessException;
import com.nxtappz.nspace.exceptions.DataNotFoundException;
import com.nxtappz.nspace.repositories.studentmanagement.StudentRepository;
import com.nxtappz.nspace.security.services.JwtService;
import com.nxtappz.nspace.services.studentmanagement.StudentService;
import com.nxtappz.nspace.services.usermanagement.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final AuthService authService;

    @Override
    public Student insertStudent(Student student) {

        Student exStudent = studentRepository.findByStudentId(student.getStudentId());
        if (null != exStudent) {
            throw new BusinessException("Student id already exists");
        }
        return saveNewStudent(student);
    }

    @Override
    public Student updateStudent(Student student) {

        Student exStudent = studentRepository.findById(student.getId())
                .orElseThrow(() -> new BusinessException("Invalid student id"));

        // TODO : validate student id
//        if (exStudent.getStudentId() == student.getStudentId()) {
//            throw new BusinessException("Student id does not exists");
//        }

        student.setUpdatedBy(authService.getCurrentUserID());
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Invalid student id"));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getOrCreateStudent(Student student) {

        Student exStudent = studentRepository.findByStudentId(student.getStudentId());
        if (null != exStudent) {
           return exStudent;
        }
        return saveNewStudent(student);
    }

    private Student saveNewStudent(Student student) {
        student.setCreatedBy(authService.getCurrentUserID());
        student.updateCreatedAt();
        return studentRepository.save(student);
    }


}
