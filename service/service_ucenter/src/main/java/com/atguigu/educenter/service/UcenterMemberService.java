package com.atguigu.educenter.service;

import com.atguigu.educenter.bean.UcenterMember;
import com.atguigu.educenter.bean.vo.LoginVo;
import com.atguigu.educenter.bean.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author carolin
 * @since 2022-05-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo member);

    void register(RegisterVo registerVo);
}
