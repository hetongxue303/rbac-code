package com.hetongxue.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hetongxue.system.domain.User;

import java.util.List;

/**
 * @Description: 用户业务
 * @InterfaceNmae: UserService
 * @Author: 何同学
 * @DateTime: 2022-05-23 19:39
 **/
public interface UserService extends IService<User> {

    /**
     * 获取所有用户
     **/
    List<User> getUserAll();

    /**
     * 根据用户名获取用户信息
     */
    User getUserByUsername(String username);

}
