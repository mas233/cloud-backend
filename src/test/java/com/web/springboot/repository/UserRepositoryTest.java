package com.web.springboot.repository;

import com.web.springboot.entity.user;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

//    @Test
    void findbyusername() {

        user user = userRepository.findByUsername("李东升");
        System.out.printf(user.getPassword());
    }
}