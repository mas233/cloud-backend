package com.web.springboot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceRepositoryTest {
    @Autowired
    private ResourceRepository resourceRepository;

//    @Test
//    void findByCourseId(){
//        System.out.println(resourceRepository.findByCourseid(1));
//    }
}