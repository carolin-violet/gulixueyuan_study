package com.atguigu.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface VodService {
    String uploadVideoAly(MultipartFile file);
}
