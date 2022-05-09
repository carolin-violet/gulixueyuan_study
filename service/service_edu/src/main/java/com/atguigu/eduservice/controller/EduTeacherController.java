package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.bean.EduTeacher;
import com.atguigu.eduservice.bean.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliExcepiton;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import java.util.List;
/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-05-03
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    //1 铲鲟讲师表所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    // 2 讲师逻辑删除
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 分页查询讲师的方法
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    // 条件查询
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery) {
        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
//
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("begin", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("end", end);
        }

        wrapper.orderByDesc("gmt_create");

        // 调用方法实现分页查询
        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    //添加讲师接口的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    // 修改讲师信息
    @PutMapping("{id}")
    public R updateTeacher(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id,

            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher) {
        eduTeacher.setId(id);
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else return R.error();
    }
}

