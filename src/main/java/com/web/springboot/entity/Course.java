package com.web.springboot.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.web.springboot.Vo.newCourseVo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 课程实体类
 */
@Entity
@Data
@Table

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String coursename;
    private String teachername;
    private List<String> studytime;
    private Integer viewtimes;
    private String introduction;

    private String department;

    private Integer remarkNum;
    private double starAverage;

//    private Remark[] remarks;
    // 课程收藏量

    @Value("0")
    private Integer collectsNum;
    // 收藏的用户的Id
    private Integer[] collectsId;
//    @Column(name = "picture", columnDefinition = "mediumblob")
    private String picture;

    public Course() {

    }

    /**
     * @return 将对象所有信息以String方式返回
     */
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Course(newCourseVo item) {
        this.coursename = item.getCoursename();
        this.teachername = item.getTeachername();
        this.studytime = item.getStudytime();
        this.viewtimes = item.getViewtimes();
        this.introduction = item.getIntroduction();
        this.department = item.getDepartment();
        this.remarkNum = item.getRemarkNum();
        this.starAverage = item.getStarAverage();
        this.collectsNum = item.getCollectsNum();
        this.collectsId = item.getCollectsId();
        this.picture = item.getPicture();
    }
}
