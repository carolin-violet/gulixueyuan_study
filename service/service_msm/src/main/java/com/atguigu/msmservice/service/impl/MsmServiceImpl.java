package com.atguigu.msmservice.service.impl;

import com.atguigu.msmservice.service.MsmService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

// 导入对应SMS模块的client
import com.tencentcloudapi.sms.v20210111.SmsClient;

// 导入要请求接口对应的request response类
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName MsmServiceImpl
 * @Description TODO
 * @Author zj
 * @Date 2022/5/14 17:56
 * @Version 1.0
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String code, String phone) {
        try {
            Credential cred = new Credential("", "");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("GET");

            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();

            SmsClient client = new SmsClient(cred, "ap-nanjing",clientProfile);
            SendSmsRequest req = new SendSmsRequest();

            String sdkAppId = "";
            req.setSmsSdkAppId(sdkAppId);

            String signName = "";
            req.setSignName(signName);

            String templateId = "";
            req.setTemplateId(templateId);

            String[] templateParamSet = {code};
            req.setTemplateParamSet(templateParamSet);

            String[] phoneNumberSet = {phone};
            req.setPhoneNumberSet(phoneNumberSet);

            SendSmsResponse res = client.SendSms(req);

            System.out.println(SendSmsResponse.toJsonString(res));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
