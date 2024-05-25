package com.web.springboot.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Target;

/**
 * 课程资源实体类
 */
@Entity
@Data
@Table
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String type;
    private String datapath;
    private Integer size;
    private Integer uploaderid;
    private Integer courseid;
    private String intro;
    private String coursename;

    private int clicktimes;

    private String uploadTime;
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
