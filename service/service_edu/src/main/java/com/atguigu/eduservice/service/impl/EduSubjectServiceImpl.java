package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.bean.EduSubject;
import com.atguigu.eduservice.bean.excel.SubjectData;
import com.atguigu.eduservice.bean.subject.OneSubject;
import com.atguigu.eduservice.bean.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-05-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 课程分类列表
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> TwoSubjectList = baseMapper.selectList(wrapperTwo);


        List<OneSubject> finalSubjectList = new ArrayList<>();

        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);

            OneSubject oneSubject = new OneSubject();

            BeanUtils.copyProperties(eduSubject, oneSubject);

            List<TwoSubject> TwoFinalSubjectList = new ArrayList<>();
            for (int m = 0; m < TwoSubjectList.size(); m++) {
                EduSubject tSubject = TwoSubjectList.get(m);
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    TwoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(TwoFinalSubjectList);
            finalSubjectList.add(oneSubject);
        }


        return finalSubjectList;
    }
}
