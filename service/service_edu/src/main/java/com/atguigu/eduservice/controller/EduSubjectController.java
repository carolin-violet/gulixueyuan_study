package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.bean.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("addSubject")
    private R addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }
}

