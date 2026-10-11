package com.learn.spring.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity 
public class Teacher {
    private String name, age, subject, experience;

    @Id
    private Long teacherId;

    public Teacher() {
        System.out.println("Teacher object created");
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
    public String getName() {
        return name;
    }
    public String getAge() {
        return age;
    }
    public String getSubject() {
        return subject;
    }
    public String getExperience() {
        return experience;
    }
}
