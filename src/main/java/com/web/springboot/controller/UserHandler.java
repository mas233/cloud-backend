package com.web.springboot.controller;

import com.web.springboot.Vo.CollectVo;
import com.web.springboot.Vo.newCourseVo;
import com.web.springboot.entity.Course;
import com.web.springboot.entity.Remark;
import com.web.springboot.entity.ResourceData;
import com.web.springboot.entity.user;
import com.web.springboot.init_opration.picture_init;
import com.web.springboot.repository.CourseRepository;
import com.web.springboot.repository.RemarkRepository;
import com.web.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RemarkRepository remarkRepository;
    @Autowired
    picture_init init;
    /**
     * 用户注册
     * url:"/user/register"
     *
     * @param user_data1 : 内为username 和 password两个字段
     * @return 字符串String
     * 用户已存在 返回 “exist”
     * 注册成功 返回 "success"
     */
    @PostMapping("/register")
    public String Register(@RequestBody user user_data1) {
        System.out.println(user_data1.toString());
        user user_data = userRepository.findByUsername(user_data1.getUsername());
        if (user_data != null) {
            return "exist";
        }
        // 现在注册出来的用户collects_id是空的，需要初始化
        user_data1.setCollects_id(new int[0]);
        if (Objects.equals(user_data1.getUsername(), "root")) {
            user_data1.setRole(true);
        } else {
            user_data1.setRole(false);
        }
        user_data1.setContribution(0);
        user receive1 = userRepository.save(user_data1);
        return "success";
    }

    /**
     * 用户登录
     * url:"/user/login"
     *
     * @param user_data_receive : 内为username 和 password两个字段
     * @return 字符串String
     * 用户不存在 返回 “not exist”
     * 登录成功 返回 “success”
     * 密码不对 返回 “fail”
     */
    @PostMapping("/login")
    public String Login(@RequestBody user user_data_receive) {
        user user_data = userRepository.findByUsername(user_data_receive.getUsername());
        if (user_data == null) {
            return "not exist";
        } else {
            boolean find = user_data_receive.getPassword().equals(user_data.getPassword());
            if (find) {
                return "success";
            } else {
                return "fail";
            }
        }
    }

    /**
     * 检查修改密码时，用户输入密码是否正确
     *
     * @param user_data_receive 包含用户名，用户输入的待检查密码
     * @return 字符串String
     *         若该用户不存在，返回error
     *         若密码正确，返回true
     *         若密码错误，返回false
     */
    @PostMapping("/modify/check")
    public String checkPasswd(@RequestBody user user_data_receive) {
        user user = userRepository.findByUsername(user_data_receive.getUsername());
        if (user == null) {
            return "error";
        }
        if (user.getPassword().equals(user_data_receive.getPassword())){
            return "true";
        } else {
            return "false";
        }
    }
    /**
     * 用户修改密码
     * url:"/modify/passwd"
     *
     * @param user_data_receive 包含新密码的用户数据（即 用户名+新密码）
     * @return 字符串String
     * 用户不存在 返回 “not exist”
     * 修改成功 返回 “success”
     * 修改失败 返回 “fail”
     */
    @PostMapping("/modify/passwd")
    @Modifying
    public String modifyPasswd(@RequestBody user user_data_receive) {
        user user = userRepository.findByUsername(user_data_receive.getUsername());
        if (user == null) {
            return "not exist";
        } else {
            user.setPassword(user_data_receive.getPassword());
            userRepository.save(user);
            user = userRepository.findByUsername(user_data_receive.getUsername());
            if (user.getPassword().equals(user_data_receive.getPassword())) {
                return "success";
            } else {
                return "fail";
            }
        }
    }

    /**
     * 用户贡献度排行榜获取
     * URL="/user/rank"
     *
     * @return 返回一个列表 ，按照贡献度降序，存贮每个用户的信息，可自行取出username和contribution字段 以及排名rank字段
     */
    @GetMapping("/rank")
    public List<rank_user> Rank() {
        List<user> users = userRepository.findByUsernameLikeOrderByContributionDesc("%");
        RankUsers rankUsers = new RankUsers(users);
        return rankUsers.getRank_userList();
    }

    /**
     * 将 List<user> 加上排名的方法类
     */
    private class RankUsers {
        List<rank_user> rank_userList = new LinkedList<>();

        public RankUsers(List<user> userList) {
            int rank = 1;
            for (user u :
                    userList) {
                rank_user one = new rank_user(u, rank++);

                rank_userList.add(one);
            }
        }

        public List<rank_user> getRank_userList() {
            return rank_userList;
        }
    }

    /**
     * 带有排名的user类
     */
    public class rank_user extends user {
        private int rank;

        public int getRank() {
            return rank;
        }

        public rank_user(user user, int rank_in) {
            rank = rank_in;
            this.setUsername(user.getUsername());
            this.setContribution(user.getContribution());
        }
    }

    /**
     * 根据用户名获取贡献值
     * url:"/getContrib/{username}"
     *
     * @param username 用户名
     * @return 其贡献值
     */
    @GetMapping("/getContrib/{username}")
    public int findContributionByUsername(@PathVariable("username") String username) {
        user user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getContribution();
        }
        return -1;
    }


    @PostMapping("/collect")
    public String collect(@RequestBody ResourceData request) {
        String username = request.getUsername();
        String CourseId = request.getCoursename();
        if (CourseId == null) {
            return "NOT FOUND COURSE";
        }
        user user = userRepository.findByUsername(username);
        if (user == null) {
            return "NOT FOUND USER";
        }
        System.out.println(username);

//        System.out.println(CourseId);
//        System.out.println(username);
        Course course = courseRepository.findById(Integer.parseInt(CourseId));
        if (course == null) {
            return "NOT FOUND COURSE";
        }
        int courseUpdateNum = course.getCollectsNum() + 1;
        course.setCollectsNum(courseUpdateNum);
        courseRepository.AddCollectsId(user.getId(), Integer.parseInt(CourseId));
        userRepository.userAddCollectCourse(user.getId(), Integer.parseInt(CourseId));
        return user.toString();
    }

    @PostMapping("/newCourse")
    public String createCourse(@RequestBody newCourseVo item){
//        User user = userRepository.findById(userId);
        Course course = new Course(item);

        String userName = item.getUploaderName();
        user U = userRepository.findByUsername(userName);
//        if (U == null) {
//            return "请先登录";
//        }

        if (!userName.equals("root")) {
            return "只有管理员可以创建课程";
        }


        if (!Objects.equals(course.getDepartment(), "人工智能学院") && !Objects.equals(course.getDepartment(), "软件学院") && !Objects.equals(course.getDepartment(), "计算机科学与技术系")) {
            return "该学院或系暂未收入数据库";
        }

        if (courseRepository.findByCoursename(course.getCoursename()) != null) {
            return "该课程已录入";
        }
        course.setCollectsNum(0);
        course.setViewtimes(0);
        course.setRemarkNum(0);
        course.setStarAverage(0);


        courseRepository.save(course);
        return "success";
    }

    @GetMapping("/getUserInfo")
    public user getUserInfo(String userName) {
        return userRepository.findByUsername(userName);
    }
    @PostMapping("/remark")
    public String publishRemark(@RequestBody Remark remark) {
        Integer courseId = remark.getCourseId();
        String userName = remark.getUserName();


        if (courseRepository.findById(courseId).isEmpty()) {
            return "NOT FOUND COURSE";
        }
        if (userRepository.findByUsername(userName) == null) {
            return "请先注册或者登录后评论";
        }
        int courseid = courseId;
        Course ownerCourse = courseRepository.findById(courseid);
        ownerCourse.setRemarkNum(ownerCourse.getRemarkNum() + 1);
        List<Remark> remarks = remarkRepository.findByCourseId(courseId);
        int num = remarks.size();
        int sumStar = 0;
        for (Remark remarkDemo : remarks) {
            sumStar += remarkDemo.getStar();
        }
        ownerCourse.setStarAverage((sumStar  *  1.0 + remark.getStar())/ (num + 1));
        courseRepository.save(ownerCourse);
        remarkRepository.save(remark);
        return remark.toString();
    }

    @GetMapping("/{courseId}/remarks")
    public List<Remark> allRemarks(@PathVariable Integer courseId) {
        return remarkRepository.findByCourseId(courseId);
    }

    @GetMapping("/isCollected/{userid}/{coursename}")
    public String getCollectStatus(@PathVariable String userid, @PathVariable String coursename) {
        int userId = userRepository.findByUsername(userid).getId();
        int courseId = courseRepository.findByCoursename(coursename).getId();
        int flag = 0;
        Course course = courseRepository.findById(courseId);
        for (int i = 0; i < course.getCollectsNum(); i++) {
            if (course.getCollectsId()[i] == userId) {
                flag = 1;
                break;
            }
        }
        if (flag == 1) {
            return "true";
        }
        return "false";
    }

    @GetMapping("/{userId}/allCollects")
    public List<Course> getAllCollects(@PathVariable int userId) {
        user user = userRepository.findById(userId);
        List<Course> list = new ArrayList<>();
        for (int i = 0; i < user.getCollects_id().length; i++) {
            list.add(courseRepository.findById(user.getCollects_id()[i]));
        }
        return list;
    }

    @PostMapping("/{remarkId}/{userId}/deleteRemark")
    public String deleteRemark(@PathVariable int remarkId, @PathVariable int  userId) {
        // 如果评论发布者名字与当前登录人不同，则不允许删除
        Remark remark = remarkRepository.findById(remarkId);
        if (!remark.getUserName().equals(userRepository.findById(userId).getUsername())) {
            return "fail";
        }
        remarkRepository.delete(remark);

        // 删除评论后，更新课程中评论的数量和平均星级
        int courseId = remark.getCourseId();
        Course course = courseRepository.findById(courseId);
        course.setRemarkNum(course.getRemarkNum() - 1);
        System.out.println(course.getRemarkNum());
        List<Remark> remarks = remarkRepository.findByCourseId(courseId);
        int num = remarks.size();
        int sumStar = 0;
        for (Remark remarkDemo : remarks) {
            sumStar += remarkDemo.getStar();
        }
        if (num == 0) {
            course.setStarAverage(0);
        } else {
            course.setStarAverage(sumStar * 1.0 / num);
        }
        courseRepository.save(course);

        return "success";
    }

    @PostMapping("/deleteCollect")
    public String deleteCollect(@RequestBody ResourceData request) {
        String username = request.getUsername();
        String CourseId = request.getCoursename();

        user U = userRepository.findByUsername(username);
        if (U == null) {
            return "NOT FOUND USER";
        }
        Course C = courseRepository.findById(Integer.parseInt(CourseId));
        if (C == null) {
            return "NOT FOUND COURSE";
        }


        courseRepository.DeleteCollectsId(U.getId(), Integer.parseInt(CourseId));
        userRepository.deleteCollectsId(U.getId(), Integer.parseInt(CourseId));
        // 取消收藏后，更新课程中收藏的数量
        courseRepository.AfterDeleteCollectsId(Integer.parseInt(CourseId));


        return "success";
    }

}
