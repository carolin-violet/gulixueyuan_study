package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName EduLoginController
 * @Description TODO
 * @Author zj
 * @Date 2022/5/6 16:32
 * @Version 1.0
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("eduservice/user")
@CrossOrigin
public class EduLoginController {

    // 登录
    @PostMapping("login")
    public R Login() {
        return R.ok().data("token", "admin");
    }

    // info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201806%2F06%2F20180606090521_ucqon.thumb.400_0.gif&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654419161&t=db4ea093bae512347356b58fcddf187e");
    }
}
