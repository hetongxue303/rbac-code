package com.hetongxue.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description: 验证码异常类
 * @ClassNmae: CaptchaException
 * @Author: 何同学
 * @DateTime: 2022-05-26 15:05
 **/
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }

    public CaptchaException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
