package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.bean.EduCourse;
import com.atguigu.eduservice.bean.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName IndexFrontController
 * @Description TODO
 * @Author zj
 * @Date 2022/5/13 11:39
 * @Version 1.0
 */

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    // 查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public R index() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);

        QueryWrapper<EduTeacher> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapper1);
        return R.ok().data("eduList", eduList).data("teacherList", teacherList);
    }
}
