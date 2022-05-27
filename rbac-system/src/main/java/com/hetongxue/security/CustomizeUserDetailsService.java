package com.hetongxue.security;

import cn.hutool.core.util.ObjectUtil;
import com.hetongxue.system.domain.User;
import com.hetongxue.system.service.RoleService;
import com.hetongxue.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 自定义UserDetailsService
 * @ClassNmae: CustomizeUserDetailsService
 * @Author: 何同学
 * @DateTime: 2022-05-27 13:34
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class CustomizeUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (ObjectUtil.isEmpty(user)) throw new UsernameNotFoundException("用户名或密码错误");
        user.setRoles(roleService.getRoleByUserID(user.getId()));
        System.out.println("用户信息:" + user);
        return user;
    }

}
