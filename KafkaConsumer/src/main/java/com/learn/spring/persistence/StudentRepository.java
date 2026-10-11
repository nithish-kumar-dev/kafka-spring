package com.learn.spring.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.learn.spring.model.Student;

@Repository 
@Component 
public interface StudentRepository extends JpaRepository<Student, Long>{
    Optional<Student> findUserByName(String name);
    
}
