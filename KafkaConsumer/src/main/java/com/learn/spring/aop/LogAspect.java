package com.learn.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
    
    @Before ("execution(* com.learn.spring.controller.StudentController.getStudents(..))")
    public void logAspect() {
        System.out.println("LogAspect method called");
    }

     @After ("execution(* com.learn.spring.controller.StudentController.*(..))")
    public void logAfterAspect() {
        System.out.println("LogAspect after method called");
    }

    @Around("execution(* com.learn.spring.controller.StudentController.*(..))")
    public Object logAroundAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("LogAspect around method called before proceeding");
        Object result = joinPoint.proceed();
        System.out.println("Target object: " + joinPoint.getThis());
        
        System.out.println("LogAspect around method called after proceeding");
        return result;
    }
}
