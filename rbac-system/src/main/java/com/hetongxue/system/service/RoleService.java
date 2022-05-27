package com.hetongxue.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hetongxue.system.domain.Role;

import java.util.List;

/**
 * @Description: 角色业务
 * @InterfaceNmae: RoleService
 * @Author: 何同学
 * @DateTime: 2022-05-26 14:40
 **/
public interface RoleService extends IService<Role> {

    /**
     * 根据用户ID获取用户对应的角色信息
     */
    List<Role> getRoleByUserID(Long userId);

}
