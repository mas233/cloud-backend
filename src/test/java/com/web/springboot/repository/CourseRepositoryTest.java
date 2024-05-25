package com.web.springboot.repository;

import com.web.springboot.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;
//
//    @Test
//    void findAll() {
//        System.out.println(courseRepository.findAll());
//    }
//
//    @Test
//    void findByName(){
//        System.out.println(courseRepository.findByCoursenameLike("%计算%"));
//    }
}