package com.web.springboot.controller;

import com.web.springboot.repository.ResourceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.web.springboot.entity.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseHandlerTest {
    @Autowired
    CourseHandler courseHandler;
//    @Test
    void findCourseNameById() {
        System.out.println(courseHandler.findCourseNameById(2));
    }
}