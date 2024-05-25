package com.web.springboot;

import com.web.springboot.entity.Course;
import com.web.springboot.entity.Resource;
import com.web.springboot.repository.CourseRepository;
import com.web.springboot.repository.ResourceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(CourseRepository repo, ResourceRepository repo2) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

            }
        };
    }
}
