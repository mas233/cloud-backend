package com.web.springboot.controller;
import java.net.HttpURLConnection;
import java.net.URL;
import com.web.springboot.entity.Course;
import com.web.springboot.entity.Resource;
import com.web.springboot.entity.ResourceData;
import com.web.springboot.entity.user;
import com.web.springboot.repository.CourseRepository;
import com.web.springboot.repository.ResourceRepository;
import com.web.springboot.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import java.io.*;

import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceHandler {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResourceRepository resourceRepository;


    /**
     * //TODO 在本地跑后端，这里要改成自己文件夹
     */
    private String path_file = "/home/ubuntu/hci/";
    //private String path_file = "C:\\Users\\administered\\Desktop\\test\\";
    private final Logger logger = LoggerFactory.getLogger(ResourceHandler.class);

    /**
     * 根据课程名字搜索该课程的资源
     * url:"/findByCoursename/{coursename}"
     *
     * @param coursename 该资源所属课程id
     * @return 该课程的所有资源列表
     */
    @GetMapping("/findByCoursename/{coursename}")
    public List<Resource> findByCourseName(@PathVariable("coursename") String coursename) {
        Course course = courseRepository.findByCoursenameLike(coursename).get(0);
        course.setViewtimes(course.getViewtimes() + 1);
        return resourceRepository.findByCourseid(course.getId());
    }

    @PostMapping("/deleteLink/{linkId}")
    public String deleteLink(@PathVariable("linkId") Integer linkId) {
        if (resourceRepository.findById(linkId).isEmpty()) {
            return "not exist";
        }
        Resource resource = (resourceRepository.findById(linkId).get());
        resourceRepository.delete(resource);
        return "success";
    }

    @PostMapping("/clicktimesPlusOne/{resourceId}")
    public String clicktimesPlusOne(@PathVariable("resourceId") Integer resourceId) {
        if (resourceRepository.findById(resourceId).isEmpty()) {
            return "not exist";
        }
        Resource resource = (resourceRepository.findById(resourceId).get());
        resource.setClicktimes(resource.getClicktimes() + 1);
        resourceRepository.save(resource);
        return "success";
    }
    @PostMapping("/uploadlink")
    public String uploadLink(@RequestBody ResourceData request) {
        logger.info("--------------------------------------------------------------");
        if (request.getUsername() == null) {
            logger.warn("前端传来 缺少字段: username");
            return "lost_username";
        }
        String name = request.getName();
        String coursename = request.getCoursename();
        String username = request.getUsername();
        String type = request.getType();
        String uploadTime = request.getUploadTime();
        String intro = request.getIntro();

        int uploaderID;
        int courseID;
        try {
            uploaderID = userRepository.findByUsername(username).getId();
            courseID = courseRepository.findByCoursename(coursename).getId();
        } catch (NullPointerException e) {
            logger.warn("用户不存在/课程不存在");
            return "user_not_exist";
        }
        String datapath;


        logger.info("进入上传方法");
        logger.info("传入数据：" + request);

        if (type.equals("链接")) {
            logger.info("资源类型：链接");
            datapath = request.getInterlinking();
        } else {
            logger.warn("未识别资源类型！");
            return "error_type";
        }


        if (resourceRepository.findByName(name) != null) {
            logger.warn("上传重复文件");
            return "link_exist";
        }
//        {
//            Resource resource2save = new Resource();
//            resource2save.setCourseid(courseID);
//            resource2save.setSize(0);
//            resource2save.setDatapath(datapath);
//            resource2save.setUploaderid(uploaderID);
//            resource2save.setName(name);
//            resource2save.setIntro(intro);
//            resource2save.setType("link");
//            Resource res = resourceRepository.save(resource2save);
//            if (res.getDatapath() == null) {
//                logger.warn("数据库添加文件信息失败（可能）");
//                return "fail";
//            }
//            logger.info("资源:数据库储存成功");
//        }

        try {
            URL url = new URL(datapath);
            HttpURLConnection connt = (HttpURLConnection)url.openConnection();
            connt.setConnectTimeout(2000);
            logger.info(connt.toString());
            //也可以通过判断code码判断是否有效
            //一般是返回200 但是不保证有些网站请求成功返回的不是200
            int responseCode = connt.getResponseCode();
            logger.info(String.valueOf(responseCode));
            if (responseCode == 200) {
                Resource resource2save = new Resource();
                resource2save.setCourseid(courseID);
                resource2save.setSize(0);
                resource2save.setDatapath(datapath);
                resource2save.setUploaderid(uploaderID);
                resource2save.setName(name);
                resource2save.setIntro(intro);
                resource2save.setUploadTime(uploadTime);
                resource2save.setType("link");
                resource2save.setCoursename(coursename);
                Resource res = resourceRepository.save(resource2save);
                if (res.getDatapath() == null) {
                    logger.warn("数据库添加文件信息失败（可能）");
                    return "fail";
                }
                logger.info("资源:数据库储存成功");
            } else {
                logger.warn("链接无法访问，请更换链接");
                return "fail";
            }
        } catch (Exception e) {
            logger.info("链接有误");
            return "fail";
        }
        {
            try {
                user user = userRepository.findById(uploaderID);
                user.setContribution(user.getContribution() + 1);
                userRepository.save(user);
                logger.info("上传者信息更新成功");
            } catch (Exception e) {
                e.printStackTrace();
                logger.warn("用户查找失败");
            }
        }
        logger.info("--------------------------------------------------------------");
        return "success";
    }

    /**
     * @param request 传入参数：json对象 (包含
     *                file:文件
     *                coursename: 对应的课程名
     *                username: 上传者的用户名
     *                name: 资源的名称
     *                intro: 资源的简介
     *                )
     * @return 返回状态："empty_file":文件为空文件（可能传文件出了问题）
     * "exists":文件已存在（根据文件 的上传名字判断）*注意区分文件名和文件上传的名字*
     * "fail": 后端问题，上传失败（可以看看后端log，联系lds）
     * "success": 上传成功
     * "lost_*": * 为字段，即缺少指定字段
     */
    @PostMapping("/uploadfile")
    public String uploadFile(HttpServletRequest request) {
        String[] params = {"name", "coursename", "username", "type", "intro","uploadTime"};
        for (String param : params) {
            if (request.getParameter(param) == null) {
                logger.warn("Front end is missing field: " + param); // "前端传来 缺少字段:" + param
                return "lost_" + param;
            }
        }

        String name = request.getParameter("name");
        String coursename = request.getParameter("coursename");
        String username = request.getParameter("username");
        String type = request.getParameter("type");
        String intro = request.getParameter("intro");
        String uploadTime = request.getParameter("uploadTime");
        int uploaderID;
        int courseID;
        try {
            uploaderID = userRepository.findByUsername(username).getId();
            courseID = courseRepository.findByCoursename(coursename).getId();
        } catch (NullPointerException e) {
            logger.warn("User does not exist"); // "用户不存在"
            return "user_not_exist";
        }
        String datapath;

        logger.info("Entering upload method"); // "进入上传方法"
        logger.warn("Please first go to springboot/controller/ResourceHandler.java and change the TODO location to your own file path (absolute path)"); // "请 先到 springboot/controller/ResourceHandler.java  中 TODO位置更换到自己要存文件的路径（绝对路径）"
        logger.info("Input data: " + request); // "传入数据：" + request
        MultipartHttpServletRequest multipartRequest =
                WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile file = multipartRequest.getFile("file");
        if (type.equals("文件")) {
            logger.info("Resource type: File"); // "资源类型：文件"
            if (file.isEmpty()) {
                logger.warn("\n !!! : File is empty\n"); // "\n !!! : 文件为空\n"
                return "empty_file";
            }
            if (resourceRepository.findByName(request.getParameter("name")) != null) {
                logger.warn("Repeatedly added file " + request.getParameter("name")); // "重复添加了文件  " + request.getParameter("name")
                return "exists";
            }
            String filePath = path_file;
            String fileName = file.getOriginalFilename();
            datapath = path_file + fileName;

            File dest = null;
            try {
                dest = new File(filePath + fileName);
            } catch (NullPointerException e) {
                logger.warn("Failed to create local file, incorrect file path, please modify it to your own path in ResourceHandler"); // "创建本地文件失败，文件路径错误，请到ResourceHandler中修改到自己的路径"
                return "fail";
            }

            try {
                file.transferTo(dest);
            } catch (IOException e) {
                logger.warn("File write failed"); // "文件写入失败"
                return "fail";
            }
            logger.info("File processing complete"); // "文件处理完成"
        } else {
            logger.warn("Unrecognized resource type!"); // "未识别资源类型！"
            return "error_type";
        }

        // Save the resource to the database
        Resource resource2save = new Resource();
        resource2save.setCoursename(courseRepository.findById(courseID).getCoursename());
        resource2save.setCourseid(courseID);
        resource2save.setUploadTime(uploadTime);
        resource2save.setType(type);
        resource2save.setSize((int) file.getSize());
        resource2save.setDatapath(datapath);
        resource2save.setUploaderid(uploaderID);
        resource2save.setName(name);
        resource2save.setIntro(request.getParameter("intro"));
        logger.info("Uploading file " + resource2save.getName() + " to " + resource2save.getDatapath()); // "上传文件 " + resource2save.getName() + " 到 " + resource2save.getDatapath()
        Resource res = resourceRepository.save(resource2save);
        if (res.getDatapath() == null) {
            logger.warn("Database failed to add file information (possibly)"); // "数据库添加文件信息失败（可能）"
            return "fail";
        }
        logger.info("Resource: Database storage successful"); // "资源:数据库储存成功"

        // Update uploader information
        try {
            user user = userRepository.findById(uploaderID);
            user.setContribution(user.getContribution() + 1);
            userRepository.save(user);
            logger.info("Uploader information updated successfully"); // "上传者信息更新成功"
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("User lookup failed"); // "用户查找失败"
        }

        return "success";
    }


    /**
     * 实现文件下载
     * url:"/downloadfile/{resourceId}
     *
     * @param response
     * @param resourceId 资源对应的id
     * @return path error: 数据库中datapath为null
     * not exist：文件不存在
     * fail：文件下载失败
     * success：下载成功
     */
    @GetMapping("/downloadfile")
    public String downloadFile(@RequestParam int resourceId, HttpServletRequest request, HttpServletResponse response) {
        Resource target = resourceRepository.findById(resourceId);
        String url = target.getDatapath();
        int index = 0;
        for (int i = url.length()-1 ; i >= 0 ; --i) {
            if (url.charAt(i) == '/') {
                index = i;
                break;
            }
        }
        if (url == null) {
            logger.error("无法读取文件路径");
            return "path error";
        }
        File file = new File(url);
        if (!file.exists()) {
            logger.warn("文件不存在");
            return "not exist";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(url.substring(index+1)));

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            logger.error("下载失败");
            return "fail";
        }
        return "success";
    }

    /**
     * 对资源进行弱模糊搜索
     * 并返回一个列表
     * 搜索模式：匹配关键字{name}，关键字必须完整
     * url:/findByName/{name}
     *
     * @return 匹配到的资源列表
     */
    @GetMapping("/findByName/{name}")
    public List<Resource> findByName(@PathVariable("name") String name) {
        return resourceRepository.findByNameLike("%" + name + "%");
    }
}
