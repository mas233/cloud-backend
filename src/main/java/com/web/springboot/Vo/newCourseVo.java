package com.web.springboot.Vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
public class newCourseVo {
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

    private String uploaderName;
}
