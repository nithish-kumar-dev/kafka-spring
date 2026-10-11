package com.learn.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.context.annotation.Bean;

@Entity 
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long studentId;
	private String name;
	private int age;
	private String address;
	private String password;

	@ManyToOne
	@JoinColumn(name = "teacherId")
	private Teacher teacher;


	public Student() {
		System.out.println("Student object created with default constructor");
	}

	public Student(String name, int age) {
		this(name,age,null);
	}
	public Student(String name, int age, Teacher teacher) {
		this.name = name;
		this.age = age;
		this.teacher = teacher;
		this.address = null;
		System.out.println("Student object created with name: " + name + " and age: " + age + " and teacher: " + teacher + " and address: " + address);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("value of name: " + name + " ");
		this.name = name;
	}

	public int getAge() {
		return age;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public String getAddress() {
		return address;
	}

	public void setAge(int age) {
		System.out.println("11111value of age: " + age + " ");
		this.age = age;
	}
	public void setTeacher(Teacher teacher) {
		System.out.println("value of teacher: " + teacher + " ");
		this.teacher = teacher;
	}
	public void setAddress(String address) {
		System.out.println("value of address: " + address + " ");
		this.address = address;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", teacher=" + teacher + ", address=" + address + "]";
	}
}
