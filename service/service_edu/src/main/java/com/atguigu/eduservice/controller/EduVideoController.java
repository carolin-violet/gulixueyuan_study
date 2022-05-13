package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.bean.EduVideo;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliExcepiton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-05-08
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    // 添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    // 删除小节
    // TODO 后面这个方法需要完善：删除小节的时候，同时删除里面的视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {

        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            R res = vodClient.removeAlyVideo(videoSourceId);
            if (res.getCode() == 20001) {
                throw new GuliExcepiton(20001, "删除视频失败,熔断器...");
            }
        }

        videoService.removeById(id);
        return R.ok();
    }

    // 修改小节

}

