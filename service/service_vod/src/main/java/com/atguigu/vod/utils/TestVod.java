package com.atguigu.vod.utils;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import org.junit.Test;

import java.util.List;


/**
 * @ClassName TestVod
 * @Description TODO
 * @Author zj
 * @Date 2022/5/11 14:57
 * @Version 1.0
 */
public class TestVod {

    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("c8150ab500234ed484e79a62db23b453");
        return client.getAcsResponse(request);
    }


    // 获取视频播放地址
    @Test
    public void test1() throws ClientException {
        // 根据视频id获取视频播放地址
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5t8SkVN1cucQePFFeG6U", "AdeWfT2YNGvDlfukr4yMg2HduT4LBE");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("c8150ab500234ed484e79a62db23b453");
        return client.getAcsResponse(request);
    }

    /*以下为调用示例*/
    @Test
    public void test2() throws ClientException {
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5t8SkVN1cucQePFFeG6U", "AdeWfT2YNGvDlfukr4yMg2HduT4LBE");
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = getVideoPlayAuth(client);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /**
     * 删除视频
     * @param client 发送请求客户端
     * @return DeleteVideoResponse 删除视频响应数据
     * @throws Exception
     */
    public static DeleteVideoResponse deleteVideo(DefaultAcsClient client) throws Exception {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds("c8150ab500234ed484e79a62db23b453");
        return client.getAcsResponse(request);
    }

    /*请求示例*/
    @Test
    public void test3() throws ClientException {
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5t8SkVN1cucQePFFeG6U", "AdeWfT2YNGvDlfukr4yMg2HduT4LBE");
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            response = deleteVideo(client);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }


    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    @Test
    public void testUploadVideo() {
        String accessKeyId = "LTAI5t8SkVN1cucQePFFeG6U";
        String accessKeySecret = "AdeWfT2YNGvDlfukr4yMg2HduT4LBE";
        String title = "奥里给";
        String fileName = "C:\\Users\\zj\\Desktop\\谷粒学院\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";

        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

}
