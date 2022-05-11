package com.atguigu.eduservice.bean.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ChapterVo
 * @Description TODO
 * @Author zj
 * @Date 2022/5/9 15:24
 * @Version 1.0
 */

@Data
public class ChapterVo {
    private String id;

    private String title;

    // 表示小姐
    private List<VideoVo> children = new ArrayList<>();
}
