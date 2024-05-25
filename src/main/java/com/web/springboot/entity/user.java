package com.web.springboot.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * 用户实体类
 */
@Entity
@Data
@Table(name = "user1")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @Value("not set")
    private String picture;
    private Integer contribution;
    @Value("0")
    private int[] collects_id;

    private boolean role;

    /**
     * @return 将对象所有信息以String方式返回
     */
    public String toString(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
