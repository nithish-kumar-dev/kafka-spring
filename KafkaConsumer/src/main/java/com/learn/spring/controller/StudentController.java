package com.learn.spring.controller;
import com.learn.spring.model.Student;
import com.learn.spring.service.StudentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class StudentController {
    
    @Autowired
    StudentService studentService;
    @GetMapping("/")
    public String welcome() {
        return "Welcome to our Spring application";
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PostMapping("/student")
    @Transactional
    public String createStudent(@RequestBody Student student) throws Exception {
        studentService.createStudent(student);
        return "Student created successfully";
    }

    @PutMapping("/student/{id}")
    public String updateStudent(@RequestBody Student student, @PathVariable Long id) {
        studentService.updateStudent(student, id);
        return "Student updated successfully";
    }
    
    @DeleteMapping("/student/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody Student student) {
        System.out.println("Login attempt for student: " + student.getName());
        return studentService.verify(student);
    }

}