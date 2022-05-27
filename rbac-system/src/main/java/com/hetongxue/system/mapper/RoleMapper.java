package com.hetongxue.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hetongxue.system.domain.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: 角色Mapper
 * @InterfaceNmae: RoleMapper
 * @Author: 何同学
 * @DateTime: 2022-05-26 14:39
 **/
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID获取用户对应的角色信息
     */
    @Select("select r.id,r.name,r.notes from r_role r,r_user_role ur where r.id = ur.id and ur.id = #{userId}")
    List<Role> getRoleByUserID(Long userId);

}
