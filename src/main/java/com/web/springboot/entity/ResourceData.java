package com.web.springboot.entity;

import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 后端接受 文件 的类型（继承 resource类 ）
 */
@Data
@Table
public class ResourceData extends Resource {
    private String coursename;
    private String username;
    private String interlinking;
    private String file;

    public ResourceData(String coursename1, String username1, String name2, String intro) {
        coursename = coursename1;
        username = username1;
        setName(name2);
        setIntro(intro);
    }

}
