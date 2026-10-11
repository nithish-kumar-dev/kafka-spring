package com.learn.spring.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.learn.spring.persistence.StudentPrincipal;
import com.learn.spring.persistence.StudentRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{
    
    @Autowired
    private StudentRepository studentRepo;


    @Override 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implement your custom user lookup logic here
        // For example, you can fetch the user from a database
        // If the user is not found, throw UsernameNotFoundException
        System.out.println("Loading user by username: " + username);
        return studentRepo.findUserByName(username)
                .map(StudentPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
