package com.hetongxue.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 登录VO
 * @ClassNmae: UsernameAndPasswordLoginVo
 * @Author: 何同学
 * @DateTime: 2022-05-23 19:35
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UsernameAndPasswordLoginVo implements Serializable {

    private String username;
    private String password;
    private String verifyCode;
    private boolean rememberMe;

}
