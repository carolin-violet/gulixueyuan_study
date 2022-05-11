package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName VodController
 * @Description TODO
 * @Author zj
 * @Date 2022/5/11 16:50
 * @Version 1.0
 */

@RestController
@CrossOrigin
@RequestMapping("/eduservice/video")
public class VodController {

    @Autowired
    private VodService vodService;

    // 上传视频到阿里云
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file) {
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId", videoId);
    }
}
