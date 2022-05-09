package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @ClassName OssServiceImpl
 * @Description TODO
 * @Author zj
 * @Date 2022/5/7 12:10
 * @Version 1.0
 */

@Service
public class OssServiceImpl implements OssService {

    // 上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 获取文件名字
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;

            // 创建PutObject请求。
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();

            // 把上传之后文件路径返回
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
