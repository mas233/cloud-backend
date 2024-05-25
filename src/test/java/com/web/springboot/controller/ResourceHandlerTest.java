package com.web.springboot.controller;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceHandlerTest {
    @Autowired
    private ResourceHandler resourceHandler = new ResourceHandler();

//    @Test
    void findByResourceName() {
        System.out.println(resourceHandler.findByName("计网"));
    }

//    @Test
    void findByCoursename(){
        System.out.println(resourceHandler.findByCourseName("互联网计算"));
    }


}