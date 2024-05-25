package com.web.springboot.init_opration;

import com.web.springboot.entity.Course;
import com.web.springboot.repository.CourseRepository;
import com.web.springboot.repository.ResourceRepository;
import com.web.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
@RestController
public class picture_init {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResourceRepository resourceRepository;

//    public void add_picture(String CourseName, String picture_path) {
//        Course course = courseRepository.findByCoursename(CourseName);
//
//        File file = new File(picture_path);
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
//            byte[] b = new byte[1000];
//            int n;
//            while ((n = fis.read(b)) != -1) {
//                bos.write(b, 0, n);
//            }
//            fis.close();
//            byte[] data = bos.toByteArray();
//            course.setPicture(data);
//            courseRepository.save(course);
//            bos.close();
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return;
//    }

}
