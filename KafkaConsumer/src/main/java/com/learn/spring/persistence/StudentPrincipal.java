package com.learn.spring.persistence;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.learn.spring.model.Student;

import java.util.Collection;
import java.util.Collections;

public class StudentPrincipal implements UserDetails {
    private final Student student;

    public StudentPrincipal(Student student) {
        this.student = student;
    }
    public Student StudentPrincipal() {
        return this.student;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        System.out.println("Getting authorities for student: " + student.getName());
        return Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
        // throw UserDetails.new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return student.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
    }

}