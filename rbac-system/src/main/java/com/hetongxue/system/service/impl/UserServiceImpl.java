package com.hetongxue.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hetongxue.system.domain.User;
import com.hetongxue.system.mapper.UserMapper;
import com.hetongxue.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 用户业务处理
 * @ClassNmae: UserServiceImpl
 * @Author: 何同学
 * @DateTime: 2022-05-23 19:39
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
//    private final RoleMapper roleMapper;

//    @Override
//    @Transactional(propagation = Propagation.SUPPORTS)
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
//        if (ObjectUtil.isEmpty(user)) throw new UsernameNotFoundException("用户名或密码错误");
//        user.setRoles(roleMapper.getRoleByUserID(user.getId()));
//        return user;
//    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> getUserAll() {
        return userMapper.selectList(null);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }


}
