package com.web.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


@RestController
@RequestMapping("/log")
public class LogController {
    String log_path = "D:\\1repo\\人机交互\\project\\Internet-Computing-WEB-main\\Internet-Computing-WEB-main\\log";

    @GetMapping("/getlog")
    public String getLog(HttpServletRequest request) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(log_path));
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = in.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
        }
        return sb.toString();
    }
}
