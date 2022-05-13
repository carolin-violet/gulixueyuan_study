package com.atguigu.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName CmsApplication
 * @Description TODO
 * @Author zj
 * @Date 2022/5/13 10:34
 * @Version 1.0
 */

@SpringBootApplication
@ComponentScan({"com.atguigu"})
@MapperScan("com.atguigu.educms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
