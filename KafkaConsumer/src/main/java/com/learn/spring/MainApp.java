package com.learn.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import com.learn.spring.service.KafkaService;

import com.learn.spring.model.Student;

import org.springframework.boot.SpringApplication;

// import com.learn.model.Student;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportResource ("applicationContext.xml")
public class MainApp {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MainApp.class, args);
		// ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		Student student = (Student) context.getBean("studentBean");
		Student student1 = (Student) context.getBean("studentBean");
		student.setName("Jane Doe");
		student1.setName("kumar");
		System.out.println(student.getName());
		System.out.println(student1.getName());
		KafkaService kafkaService = (KafkaService) context.getBean("kafkaService");
		kafkaService.consumeKafkaData();
	}
}