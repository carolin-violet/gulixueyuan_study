package com.atguigu.eduservice.bean.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OneSubject
 * @Description TODO
 * @Author zj
 * @Date 2022/5/8 9:23
 * @Version 1.0
 */

@Data
public class OneSubject {
    private String id;
    private String title;

    private List<TwoSubject> children = new ArrayList<>();
}
