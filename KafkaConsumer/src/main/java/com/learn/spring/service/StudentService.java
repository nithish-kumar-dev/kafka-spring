package com.learn.spring.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.learn.spring.model.Student;
import com.learn.spring.persistence.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentDb;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired 
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public List<Student> getStudents() {
        return studentDb.findAll();
    }

    public Student getStudent(Long id) {
        return studentDb.findById(id).orElse(null);
    }

    public Student createStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        Student st = studentDb.save(student);
        System.out.println("Student created: " + st.toString());
        System.out.println("Fetching student from db:"+studentDb.findById(st.getStudentId()).orElse(null));
        // throw new RuntimeErrorException(null);
        return st;
    }

    public Student updateStudent(Student student, Long id) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            if (!studentDb.existsById(id)) {
                throw new IllegalArgumentException("Student not found with id: " + id);
            }
            student.setStudentId(id);
            Student updated = studentDb.save(student);
            transactionManager.commit(status);
            return updated;
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    public void deleteStudent(Long id) {
        // studentDb.deleteById(id);
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            if (!studentDb.existsById(id)) {
                throw new IllegalArgumentException("Student not found with id: " + id);
            }
            studentDb.deleteById(id);
            transactionManager.commit(status);
        } catch(Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    public String verify(Student student) {
        System.out.println("Verifying student: " + student.getName());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(student.getName(), student.getPassword()));
        if(authentication.isAuthenticated()) {
            System.out.println("Student verified: " + student.getName());
        } else {
            throw new IllegalArgumentException("Invalid student credentials for: " + student.getName());
        }
        return jwtService.generateToken(student.getName());
    }
}
