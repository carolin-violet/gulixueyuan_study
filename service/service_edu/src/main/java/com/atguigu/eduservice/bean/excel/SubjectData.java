package com.atguigu.eduservice.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @ClassName SubjectData
 * @Description TODO
 * @Author zj
 * @Date 2022/5/7 21:07
 * @Version 1.0
 */

@Data
public class SubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
